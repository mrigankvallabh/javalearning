package chapterv201;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class CreatingStreams {
    public static void main(String[] args) throws IOException {
        var path = Path.of("resources", "alice30.txt");
        var contents = Files.readString(path);
        var words_array = contents.split("\\PL+"); // * Split on non-letter characters
        var words = Stream.of(words_array);
        show("words", words);

        var song = Stream.of("gently", "down", "the", "stream");
        show("song", song);

        var silence = Stream.empty();
        show("silence", silence);

        var echos = Stream.generate(() -> "Echo");
        show("echos", echos);

        var echosLimited = Stream.generate(() -> "Echo").limit(5);
        show("echosLimited", echosLimited);

        var randoms = Stream.generate(Math::random);
        show("randoms", randoms);

        var integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        show("integers", integers);

        var greetings = "Hello, World!\nIs the weather beautiful?\nI hope so.".lines();
        show("greetings", greetings);

        var wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
        show("wordsAnotherWay", wordsAnotherWay);

        try (var lines = Files.lines(path)) {
            show("lines", lines);
        }

        var iterable = FileSystems.getDefault().getRootDirectories();
        var rootDirectories = StreamSupport.stream(iterable.spliterator(), false);
        show("roots", rootDirectories);

        var iterator = Path.of("/usr/shr/lib/echo").iterator();
        var pathComponents = StreamSupport
                .stream(Spliterators
                        .spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                        false);
        show("pathComponents", pathComponents);
    }

    static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        var firstElements = stream.limit(SIZE + 1).toList();
        System.out.print(title + ": ");
        for (int i = 0; i < firstElements.size(); i++) {
            if (i > 0)
                System.out.print(", ");
            if (i < SIZE)
                System.out.print(firstElements.get(i));
            else
                System.out.print("...");
        }
        System.out.println();
    }
}
