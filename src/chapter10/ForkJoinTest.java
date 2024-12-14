package chapter10;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

final class ForkJoinTest {
    static void main(String[] args) {
        final int SIZE = 10_000_000;
        var numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = Math.random();
        }
        var counter = new Counter(numbers, 0, SIZE, x -> x > 0.5);
        try (var pool = new ForkJoinPool()) {
            pool.invoke(counter);
        }
        System.out.println(counter.join());
    }
}

final class Counter extends RecursiveTask<Integer> {
    static final int THRESHOLD = 1_000;
    private double[] values;
    private int from;
    private int to;
    private DoublePredicate filter;

    public Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        if (to - from < THRESHOLD) {
            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.test(values[i])) {
                    ++count;
                }
            }
            return count;
        }
        var mid = from + (to - from) / 2;
        var first = new Counter(values, from, mid, filter);
        var second = new Counter(values, mid, to, filter);
        invokeAll(first, second);
        return first.join() + second.join();
    }

}
