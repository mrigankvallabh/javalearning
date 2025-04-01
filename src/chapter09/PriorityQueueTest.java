package chapter09;

import java.time.LocalDate;
import java.util.PriorityQueue;

final class PriorityQueueTest {
    static void main(String[] args) {
        var pq = new PriorityQueue<LocalDate>();
        pq.add(LocalDate.of(1906, 12, 9)); // Grace Hopper
        pq.add(LocalDate.of(1815, 12, 10)); // Ada Lovelace
        pq.add(LocalDate.of(1903, 12, 3)); // John V. Neumann
        pq.add(LocalDate.of(1910, 6, 22)); // K. Zuze

        System.out.println("Iterating over Elements...");
        for (var date : pq) {
            System.out.println(date);
        }

        System.out.println("Removing Elements...");
        while (!pq.isEmpty()) {
            System.out.println(pq.remove());
        }
    }
}
