package me.theyinspire.projects.sorting.sort.impl;

import me.theyinspire.projects.sorting.sort.Sorter;
import me.theyinspire.projects.sorting.stats.BookKeeper;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.impl.DefaultBookKeeper;

import java.util.Comparator;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:35 AM)
 */
public class QuickSorter<E extends Comparable<E>> implements Sorter<E> {

    private final Comparator<E> comparator;

    public QuickSorter(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private void sort(E[] items, int from, int to, BookKeeper keeper) {
        if (from < to - 1) {
            if (to - from == 2) {
                keeper.performOperation(1);
                if (comparator.compare(items[from], items[to - 1]) > 0) {
                    keeper.performOperation(1);
                    ArrayUtils.swap(items, from, to - 1);
                }
                return;
            }
            int middle = partition(items, from, to, keeper);
            sort(items, from, middle, keeper);
            sort(items, middle + 1, to, keeper);
        }
    }

    private int partition(E[] items, int from, int to, BookKeeper keeper) {
        keeper.useMemory(1);
        keeper.performOperation(1);
        final E partition = items[to - 1];
        int smaller = from - 1;
        int seen = from;
        while (seen < to - 1) {
            keeper.performOperation(1);
            if (comparator.compare(partition, items[seen]) >= 0) {
                keeper.performOperation(1);
                smaller ++;
                ArrayUtils.swap(items, smaller, seen);
            }
            keeper.performOperation(1);
            seen ++;
        }
        keeper.performOperation(1);
        ArrayUtils.swap(items, smaller + 1, to - 1);
        keeper.releaseMemory(1);
        return smaller + 1;
    }

    @Override
    public BookKeeperStatistics sort(E[] items) {
        final BookKeeper keeper = new DefaultBookKeeper();
        keeper.useMemory(items.length);
        sort(items, 0, items.length, keeper);
        keeper.releaseMemory(items.length);
        return keeper.done();
    }

}
