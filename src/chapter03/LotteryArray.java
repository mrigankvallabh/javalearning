package chapter03;

final class LotteryArray {
    static void main(String[] args) {
        final int NMAX = 10;
        var odds = new int[NMAX + 1][];
        for (int i = 0; i <= NMAX; i++) {
            odds[i] = new int[i + 1];
        }

        for (int i = 0; i < odds.length; i++) {
            for (int j = 0; j < odds[i].length; j++) {
                int lotteryOdds = 1;
                for (int k = 1; k <= j; k++) {
                    lotteryOdds = lotteryOdds * (i - k + 1) / k;
                }
                odds[i][j] = lotteryOdds;
            }
        }

        for (var row : odds) {
            for (var odd : row) {
                System.out.printf("%4d", odd);
            }
            System.out.println();
        }
    }
}
