package chapter10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

final class ExecutorDemo {
    static long occurences(String word, Path path) {
        try (var in = new Scanner(path)) {
            int count = 0;
            while (in.hasNext()) {
                if (in.next().equals(word)) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    static Set<Path> descendents(Path rootDir) throws IOException {
        try (var entries = Files.walk(rootDir)) {
            return entries
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toSet());
        }
    }

    static Callable<Path> searchForTask(String word, Path path) {
        return () -> {
            try (var in = new Scanner(path)) {
                while (in.hasNext()) {
                    if (in.next().equals(word)) {
                        return path;
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        // System.out.println("Search in " + path + " cancelled.");
                        return null;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }

    static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter base directory: ");
            var baseDir = in.nextLine();
            System.out.print("Enter a keyword: ");
            var word = in.nextLine();

            var files = descendents(Path.of(baseDir));
            var tasks = new ArrayList<Callable<Long>>();
            for (var file : files) {
                Callable<Long> task = () -> occurences(word, file);
                tasks.add(task);
            }

            var executor = Executors.newVirtualThreadPerTaskExecutor(); // 1520ms
            // var executor = Executors.newCachedThreadPool(); // 3787ms
            // var executor = Executors.newSingleThreadExecutor(); // 7469ms
            // var executor = Executors.newFixedThreadPool(12); // 1581ms
            var startTime = Instant.now();
            var results = executor.invokeAll(tasks);
            var endTime = Instant.now();
            var total = 0L;
            for (var result : results) {
                total += result.get();
            }
            System.out.println("Occurences of " + word + ": " + total);
            System.out.println("Time elapsed: " + Duration.between(startTime, endTime).toMillis() + "ms");

            var searchTasks = new ArrayList<Callable<Path>>();
            for (var file : files) {
                searchTasks.add(searchForTask(word, file));
            }
            startTime = Instant.now();
            var found = executor.invokeAny(searchTasks);
            endTime = Instant.now();
            System.out.println(word + " occurs in: " + found);
            System.out.println("Time elapsed: " + Duration.between(startTime, endTime).toMillis() + "ms");

            executor.close();
        }
    }
}
