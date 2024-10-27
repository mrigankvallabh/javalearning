package chapter03;

import java.math.BigInteger;
import java.util.Scanner;

final class BigIntegerTest {
    static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            System.out.print("How many numbers do you need to draw? ");
            int k = in.nextInt();

            System.out.print("What is the highest number you can draw? ");
            BigInteger n = in.nextBigInteger();

            var lotteryOdds = BigInteger.ONE;
            for (int i = 1; i < k; i++) {
                lotteryOdds = lotteryOdds
                        .multiply(n.subtract(BigInteger.valueOf(i - 1)))
                        .divide(BigInteger.valueOf(i));
            }
            System.out.printf("Your odds are 1 in %s. Good Luck!%n", lotteryOdds);
        }
    }
}
