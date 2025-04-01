package chapter09;

import java.util.LinkedList;

final class LinkedListTest {
    static void main(String[] args) {
        var a = new LinkedList<String>();
        a.add("Mary");
        a.add("Robert");
        a.add("Irene");
        var b = new LinkedList<String>();
        b.add("George");
        b.add("Alice");
        b.add("Nancy");
        b.add("Kate");
        var aIter = a.listIterator();
        var bIter = b.iterator();
        while (bIter.hasNext()) {
            if (aIter.hasNext()) {
                aIter.next();
            }
            aIter.add(bIter.next());
        }
        System.out.println(a);

        // Remove every 2nd element
        bIter = b.iterator();
        while (bIter.hasNext()) {
            bIter.next(); // skip element
            if (bIter.hasNext()) {
                bIter.next(); // skip past the element to delete
                bIter.remove(); // delete the element just skipped
            }
        }
        System.out.println(b);

        a.removeAll(b);
        System.out.println(a);
    }
}