package chapter10;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class SyncBankLockTest {
    static final int NACCOUNTS = 100;
    static final double INITIAL_BALANCE = 1_000;
    static final double MAX_AMOUNT = 1_000;
    static final int DELAY = 10;

    static void main(String[] args) {
        var bank = new Bank2(NACCOUNTS, INITIAL_BALANCE);
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

final class Bank1 {
    private final double[] accounts;
    private final Lock bankLock = new ReentrantLock();
    private final Condition sufficientFunds = bankLock.newCondition();

    Bank1(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock(); // Enter critica condition after locking

        try {
            while (accounts[from] < amount) {
                sufficientFunds.await(); // Wait until some other thread adds money
            }
            System.out.println(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll(); // Signal to waiting threads
        } finally {
            bankLock.unlock();
        }
    }

    double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0.0;
            for (double d : accounts) {
                sum += d;
            }
            return sum;
        } finally {
            bankLock.unlock();
        }
    }

    int size() {
        return accounts.length;
    }
}
