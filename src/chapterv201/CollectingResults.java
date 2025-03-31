package chapterv201;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CollectingResults {
    static Stream<String> stripVowels() throws IOException {
        var contents = Files.readString(Path.of("resources", "alice30.txt"));
        return Stream.of(contents.split("\\PL+"))
                .map(s -> s.replaceAll("[AEIOUaeiou]", "")); // * Split on non-letter characters
    }

    static <T> void show(String label, Set<T> set) {
        System.out.print(label + ": " + set.getClass().getSimpleName());
        System.out.print("[" +
                set
                        .stream()
                        .limit(10)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "))
                + "]");
    }

    public static void main(String[] args) throws IOException {
        var iter = Stream.iterate(0, i -> i + 1).limit(10).iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        var numbers = Stream.iterate(0, i -> i + 1).limit(10).toArray(Integer[]::new); // toArray() returns Object[]
        System.out.println(Arrays.toString(numbers)); // * Prints [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        var noVowelSet = stripVowels()
                .collect(Collectors.toSet()); // * Collect the results into a Set
        show("noVowelSet", noVowelSet);

        var noVowelTreeSet = stripVowels()
                .collect(Collectors.toCollection(TreeSet::new)); // * Collect the results into a TreeSet
        show("noVowelTreeSet", noVowelTreeSet);

        String result = stripVowels()
                .limit(10)
                .collect(Collectors.joining());
        System.out.println("Joining: " + result);

        result = stripVowels()
                .limit(10)
                .collect(Collectors.joining(", "));
        System.out.println("Joining with commas: " + result);

        var summary = stripVowels()
                .collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Max word length: " + maxWordLength);
        System.out.println("forEach:");
        stripVowels()
                .limit(10)
                .forEach(System.out::println);
    }
}
