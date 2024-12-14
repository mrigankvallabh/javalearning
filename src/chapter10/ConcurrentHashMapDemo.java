package chapter10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

final class ConcurrentHashMapDemo {
    static final ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    static void main(String[] args) throws IOException {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var pathToRoot = Path.of(".");
        for (var p : descendants(pathToRoot)) {
            if (p.getFileName().toString().endsWith(".java")) {
                executor.execute(() -> process(p));
            }
        }
        executor.close();

        map.forEach((k, v) -> {
            if (v > 10) {
                System.out.println(k + " occurs " + v + " times");
            }
        });
    }

    static void process(Path file) {
        try (var in = new Scanner(file)) {
            while (in.hasNext()) {
                var word = in.next();
                map.merge(word, 1L, Long::sum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Set<Path> descendants(Path rootDir) throws IOException {
        try (var entries = Files.walk(rootDir)) {
            return entries.collect(Collectors.toSet());
        }
    }
}
