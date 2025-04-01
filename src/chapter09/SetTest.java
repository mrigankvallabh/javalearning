package chapter09;

import java.util.HashSet;
import java.util.Scanner;

final class SetTest {
    static void main(String[] args) {
        var words = new HashSet<String>();
        long totalTime = 0;
        try (var in = new Scanner(System.in)) {
            while (in.hasNext()) {
                var word = in.next();
                var callTime = System.currentTimeMillis();
                words.add(word);
                callTime = System.currentTimeMillis() - callTime;
                totalTime += callTime;
            }
        }
        var iter = words.iterator();
        for (int i = 1; i <= 20 && iter.hasNext(); i++) {
            System.out.println(iter.next());
        }
        System.out.println("...");
        System.out.println(words.size() + " distinct words. Total time " + totalTime + "ms");
    }
}

/*
 * Run like
 * /usr/bin/env /usr/local/java/jdk-23/bin/java --enable-preview -XX:+ShowCodeDetailsInExceptionMessages -cp /home/mrigank/coding/java_programming/Horstmann/javalearning/bin chapter09.SetTest
 */