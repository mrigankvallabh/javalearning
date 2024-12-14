package chapter10;

import java.util.Arrays;

final class ThreadTest {
    static final int DELAY = 10;
    static final int STEPS = 10;
    static final double MAX_AMOUNT = 1_000;

    static void main(String[] args) {
        var bank = new Bank2(4, 1_000_000);

        Runnable task1 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    var amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable task2 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    var amount = MAX_AMOUNT * Math.random();
                    bank.transfer(2, 3, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();
    }

}

final class Bank2 {
    private final double[] accounts;

    Bank2(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) {
            return;
        }
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
    }

    double getTotalBalance() {
        double sum = 0.0;
        for (double d : accounts) {
            sum += d;
        }
        return sum;
    }

    int size() {
        return accounts.length;
    }
}
