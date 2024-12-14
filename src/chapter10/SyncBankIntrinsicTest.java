package chapter10;

import java.util.Arrays;

final class SyncBankIntrinsicTest {
    static final int NACCOUNTS = 100;
    static final double INITIAL_BALANCE = 1_000;
    static final double MAX_AMOUNT = 1_000;
    static final int DELAY = 10;

    static void main(String[] args) {
        var bank = new Bank1(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    while (true) {
                        int toAccount = (int) (Math.random() * bank.size());
                        var amount = MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int) (DELAY * Math.random()));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread.ofPlatform().start(r);
        }

    }

}

final class Bank {
    private final double[] accounts;

    Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    synchronized void transfer(int from, int to, double amount) throws InterruptedException {
        while (accounts[from] < amount) {
            wait(); // Wait until some other thread adds money
        }
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
        notifyAll(); // Signal to waiting threads
    }

    synchronized double getTotalBalance() {
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
