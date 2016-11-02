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
public class BubbleSorter<E extends Comparable<E>> implements Sorter<E> {

    private final Comparator<E> comparator;

    public BubbleSorter(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * This algorithm sorts an input array by smaller items sink down the array
     * @param items    the items to be sorted
     */
    @Override
    public BookKeeperStatistics sort(E[] items) {
        final BookKeeper keeper = new DefaultBookKeeper(items.length, "BubbleSort");
        keeper.useMemory(items.length);
        for (int i = 0; i < items.length - 1; i ++) {
            for (int j = items.length - 1; j > i; j --) {
                keeper.performOperation(1);
                if (comparator.compare(items[j], items[j - 1]) < 0) {
                    keeper.performOperation(1);
                    ArrayUtils.swap(items, j, j - 1);
                }
            }
        }
        keeper.releaseMemory(items.length);
        return keeper.done();
    }

}
