package chapterv201;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class CountLongWords {
    public static void main(String[] args) throws IOException {
        var content = Files.readString(Path.of("resources/alice30.txt"));
        var words = List.of(content.split("\\PL+")); // split by non-letter characters
        long count = 0;
        for (var word : words) {
            if (word.length() > 12) {
                count++;
            }
        }
        System.out.println("Long words: " + count);

        count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println("Long words: " + count);

        count = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.println("Long words: " + count);
    }
}
