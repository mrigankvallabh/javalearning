package multithreading;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

final class AppleTree {
    private final String treeLabel;
    private final int nApples;

    public AppleTree(String treeLabel, int nApples) {
        this.treeLabel = treeLabel;
        this.nApples = nApples;
    }

    static AppleTree[] newTreeGarden(int size) {
        var appleTrees = new AppleTree[size];
        for (int i = 0; i < size; i++) {
            appleTrees[i] = new AppleTree("T#" + i, 3);
        }
        return appleTrees;
    }

    int pickApples(String worker) {
        try {
            System.out.printf("%s started picking apples from tree %s \n", worker, treeLabel);
            TimeUnit.SECONDS.sleep(1); // picking finished after 1s
            System.out.printf("%s picked %d apples from tree %s \n", worker, nApples, treeLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nApples;
    }

    int pickApples() {
        return pickApples(toLabel(Thread.currentThread().getName()));
    }

    private String toLabel(String threadName) {
        var threadNameToLabel = new HashMap<String, String>();
        threadNameToLabel.put("ForkJoinPool.commonPool-worker-1", "Alice");
        threadNameToLabel.put("ForkJoinPool.commonPool-worker-2", "Bob");
        threadNameToLabel.put("ForkJoinPool.commonPool-worker-3", "Claire");
        threadNameToLabel.put("ForkJoinPool.commonPool-worker-4", "Demi");
        return threadNameToLabel.getOrDefault(threadName, threadName);
    }

}

final class PickApplesWithInvokeAll {
    static void main(String[] args) throws Exception {
        var appleTrees = AppleTree.newTreeGarden(6);
        var picker1 = createApplePicker(appleTrees, 0, 2, "Alice");
        var picker2 = createApplePicker(appleTrees, 2, 4, "Bob");
        var picker3 = createApplePicker(appleTrees, 4, 6, "Claire");

        ForkJoinPool.commonPool().invokeAll(Arrays.asList(picker1, picker2, picker3));

        var total = (picker1.call() + picker2.call() + picker3.call());
        System.out.println(total + " Apples Collected");
    }

    static Callable<Integer> createApplePicker(AppleTree[] tree, int from, int to, String worker) {
        return () -> {
            int sum = 0;
            for (int i = from; i < to; i++) {
                sum += tree[i].pickApples(worker);
            }
            return sum;
        };
    }
}

final class PickApplesWithRecursiveTasks {
    static void main(String[] args) {
        var appleTrees = AppleTree.newTreeGarden(12);
        var pool = ForkJoinPool.commonPool();
        var task = new PickAppleTask(appleTrees, 0, appleTrees.length - 1);
        var result = pool.invoke(task);
        System.out.println("Total Apples picked: " + result);
    }

}

class PickAppleTask extends RecursiveTask<Integer> {
    private final AppleTree[] trees;
    private final int from, to;
    private final int THRESHOLD = 4;

    PickAppleTask(AppleTree[] trees, int from, int to) {
        this.trees = trees;
        this.from = from;
        this.to = to;
    }

    Integer doCompute() {
        return IntStream.rangeClosed(from, to)
                .map(i -> trees[i].pickApples())
                .sum();
    }

    @Override
    protected Integer compute() {
        if (to - from < THRESHOLD) {
            return doCompute();
        }
        final int mid = from + (to - from) / 2;
        var leftSum = new PickAppleTask(trees, from, mid);
        var rightSum = new PickAppleTask(trees, mid + 1, to);
        rightSum.fork();
        return leftSum.compute() + rightSum.join();
    }
}

final class PickApplesWithRecursiveActions {
    static void main(String[] args) {
        var appleTrees = AppleTree.newTreeGarden(12);
        var pool = ForkJoinPool.commonPool();
        var task = new PickAppleAction(appleTrees, 0, appleTrees.length - 1);
        pool.invoke(task);
        System.out.println("All Apples picked!");
    }

}

class PickAppleAction extends RecursiveAction {
    private final AppleTree[] trees;
    private final int from, to;
    private final int THRESHOLD = 4;

    PickAppleAction(AppleTree[] trees, int from, int to) {
        this.trees = trees;
        this.from = from;
        this.to = to;
    }

    void doCompute() {
        IntStream.rangeClosed(from, to)
                .forEach(i -> trees[i].pickApples());
    }

    @Override
    protected void compute() {
        if (to - from < THRESHOLD) {
            doCompute();
            return;
        }
        final int mid = from + (to - from) / 2;
        var leftSum = new PickAppleAction(trees, from, mid);
        var rightSum = new PickAppleAction(trees, mid + 1, to);
        rightSum.fork(); // Async
        leftSum.compute(); // Sync and in the current Thread
        rightSum.join();
    }
}
