package chapter05;

import java.util.Scanner;

final class EnumTest {
    static void main() {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter a Size: (SMALL, MEDIUM, LARGE, EXTRA_LARGE) ");
            var input = in.next().toUpperCase();
            var size = Enum.valueOf(Size.class, input);
            System.out.println("size = " + size);
            System.out.println("size abbreviated = " + size.getAbbreviation());
            if (size == Size.EXTRA_LARGE) {
                System.out.println("Fat guy!");
            }
        }
    }
}

enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    private String abbreviation;

    Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}