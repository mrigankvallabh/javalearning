package chapter03;

import java.util.Arrays;
import java.util.Scanner;

final class LotteryDrawing {
    static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            System.out.print("How many numbers do you need to draw? ");
            int k = in.nextInt();

            System.out.print("What is the highest number you can draw? ");
            int n = in.nextInt();

            var numbers = new int[n];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = i + 1;
            }

            var result = new int[k];
            for (int i = 0; i < result.length; i++) {
                int r = (int) (Math.random() * n);
                result[i] = numbers[r];
                numbers[r] = numbers[n - 1];
                --n;
            }

            Arrays.sort(result);
            System.out.println("Bet the following combination. It will make you rich!");
            for (int i : result) {
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }
    }
}
