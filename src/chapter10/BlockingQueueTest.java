package chapter10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

final class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final Path TERMINATION = Path.of("");
    private static final BlockingQueue<Path> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter base directory: ");
            var baseDir = in.nextLine();
            System.out.print("Enter a keyword: ");
            var keyword = in.nextLine();

            Runnable enumerator = () -> {
                try {
                    enumerate(Path.of(baseDir));
                    queue.put(TERMINATION);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                }
            };

            Thread.ofPlatform().start(enumerator);
            for (int i = 1; i <= SEARCH_THREADS; i++) {
                Runnable searcher = () -> {
                    try {
                        boolean done = false;
                        while (!done) {
                            var file = queue.take();
                            if (file == TERMINATION) {
                                queue.put(file);
                                done = true;
                            } else {
                                search(file, keyword);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                    }
                };
                Thread.ofPlatform().start(searcher);
            }
        }
    }

    static void enumerate(Path directory) throws IOException, InterruptedException {
        try (var children = Files.list(directory)) {
            for (var child : children.toList()) {
                if (Files.isDirectory(child)) {
                    enumerate(child);
                } else {
                    queue.put(child);
                }
            }
        }
    }

    static void search(Path file, String keyword) throws IOException {
        try (var in = new Scanner(file)) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                ++lineNumber;
                var line = in.nextLine();
                if (line.contains(keyword)) {
                    System.out.printf("%s:%d:%s%n", file, lineNumber, line);
                }
            }
        }
    }
}