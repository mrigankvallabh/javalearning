package chapterv201;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

class OptionalTest {
    public static void main(String[] args) throws IOException {
        var contents = Files.readString(Path.of("resources", "alice30.txt"));
        var wordList = List.of(contents.split("\\PL+")); // * Split on non-letter characters
        var optionalValue = wordList
                .stream()
                .filter(w -> w.contains("fred"))
                .findFirst(); // * Find the first word that contains "fred"
        System.out.println(optionalValue.orElse("No words matching 'fred' found."));

        var optionalString = Optional.empty(); // * Create an empty Optional
        var result = optionalString.orElse("N/A");
        System.out.println("result: " + result); // * Prints "N/A"
        result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("result: " + result); // * Prints "N/A"
        try {
            result = optionalString.orElseThrow(IllegalStateException::new);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        optionalValue = wordList
                .stream()
                .filter(w -> w.contains("red"))
                .findFirst(); // * Find the first word that contains "fred"
        optionalValue.ifPresent(s -> System.out.println("Found: " + s)); // * Prints "Found: red"

        var results = new HashSet<String>();
        optionalValue.ifPresent(results::add); // * Add the found word to the set
        var added = optionalValue.map(results::add);
        System.out.println("added: " + added); // * Prints "added: false"
        System.out.println("results: " + results); // * Prints "results: [red]"
        System.out.println(inverse(4.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(-1.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(0.0).flatMap(OptionalTest::squareRoot));
        var result1 = Optional.of(16.0)
                .flatMap(OptionalTest::inverse)
                .flatMap(OptionalTest::squareRoot);
        System.out.println(result1); // * Prints "Optional[0.25]"
    }

    static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
