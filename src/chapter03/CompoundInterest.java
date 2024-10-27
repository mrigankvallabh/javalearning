package chapter03;

final class CompoundInterest {
    static void main(String[] args) {
        final double START_RATE = 10.0;
        final int NRATES = 6;
        final int NYEARS = 10;

        var rates = new double[NRATES];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = (START_RATE + i) / 100.0;
        }

        var balances = new double[NYEARS][NRATES];
        for (int i = 0; i < balances[0].length; i++) {
            balances[0][i] = 10_000.0;
        }

        for (int i = 1; i < balances.length; i++) {
            for (int j = 0; j < balances[i].length; j++) {
                var oldBalance = balances[i - 1][j];
                var interest = oldBalance * rates[j];
                balances[i][j] = oldBalance + interest;
            }
        }

        for (int i = 0; i < rates.length; i++) {
            System.out.printf("%9.0f%%", 100 * rates[i]);
        }
        System.out.println();

        for (double[] row : balances) {
            for (double b : row) {
                System.out.printf("%10.2f", b);
            }
            System.out.println();
        }
    }
}
