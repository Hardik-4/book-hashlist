package books;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Separate-chaining hash table backed by an array of linked lists.
 */
public class HashList<E> {
    private final LinkedList<E>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashList(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be positive");
        }
        buckets = (LinkedList<E>[]) new LinkedList[bucketCount];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int indexFor(E value) {
        int hash = value.hashCode();
        return Math.floorMod(hash, buckets.length);
    }

    public void add(E value) {
        LinkedList<E> bucket = buckets[indexFor(value)];
        if (!bucket.contains(value)) {
            bucket.add(value);
            size++;
        }
    }

    public boolean contains(E value) {
        return buckets[indexFor(value)].contains(value);
    }

    public boolean remove(E value) {
        boolean removed = buckets[indexFor(value)].remove(value);
        if (removed) {
            size--;
        }
        return removed;
    }

    public int size() {
        return size;
    }

    public int bucketCount() {
        return buckets.length;
    }

    public void displayLists() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i].isEmpty()) {
                continue;
            }
            System.out.println("Bucket " + i + ": " + buckets[i]);
        }
    }

    public void displaySample(int maxItems) {
        int shown = 0;
        for (LinkedList<E> bucket : buckets) {
            for (E item : bucket) {
                System.out.println("  - " + item);
                shown++;
                if (shown >= maxItems) {
                    int remaining = size - shown;
                    if (remaining > 0) {
                        System.out.println("  ... and " + remaining + " more");
                    }
                    return;
                }
            }
        }
    }

    public double percentUsed() {
        int used = 0;
        for (LinkedList<E> bucket : buckets) {
            if (!bucket.isEmpty()) {
                used++;
            }
        }
        return (used * 100.0) / buckets.length;
    }

    public Set<E> getAllEntries() {
        Set<E> set = new HashSet<>();
        for (LinkedList<E> bucket : buckets) {
            set.addAll(bucket);
        }
        return set;
    }
}
