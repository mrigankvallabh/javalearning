package chapter09;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;

final class TreeSetTest {
    static void main(String[] args) {
        var parts = new TreeSet<Item>();
        parts.add(new Item(6745, "Toaster"));
        parts.add(new Item(6764, "Blender"));
        parts.add(new Item(9689, "Vacuum Cleaner"));
        parts.add(new Item(4067, "Fan"));
        parts.add(new Item(2597, "Motor"));
        parts.add(new Item(1232, "Modem"));
        System.out.println(parts); // sorted by Item.compareTo

        var sortedPartsByDescr = new TreeSet<Item>(
                Comparator.comparing(Item::getDescription).thenComparing(Item::getPartNumber));
        sortedPartsByDescr.addAll(parts);
        System.out.println(sortedPartsByDescr);
    }
}

final class Item implements Comparable<Item> {
    private final int partNumber;
    private final String description;

    public Item(int partNumber, String description) {
        this.partNumber = partNumber;
        this.description = description;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item [partNumber=" + partNumber + ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Item other = (Item) obj;

        return Objects.equals(partNumber, other.partNumber) && Objects.equals(description, other.description);
    }

    @Override
    public int compareTo(Item o) {
        var diff = Integer.compare(partNumber, o.partNumber);
        return diff != 0 ? diff : description.compareToIgnoreCase(o.description);
    }

}