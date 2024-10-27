package chapter03;

import java.util.Scanner;

final class InputTest {
    static void main() {
        try (var in = new Scanner(System.in)) {
            System.out.print("What is your name? ");
            var name = in.nextLine();
            System.out.print("How old are you? ");
            var age = in.nextInt();
            System.out.println("Hello " + name + "! Next year you will be " + (age + 1));
        }
    }    
}