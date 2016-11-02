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
public class SelectionSorter<E extends Comparable<E>> implements Sorter<E> {

    private final Comparator<E> comparator;

    public SelectionSorter(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * This method implements the "selection" sort algorithm by considering the shrinking tail of the array (which contains
     * the full array at the start) and then finding the local minimum and placing it at the beginning of the array.
     * @param items    the items to be sorted
     */
    @Override
    public BookKeeperStatistics sort(E[] items) {
        final BookKeeper keeper = new DefaultBookKeeper();
        keeper.useMemory(items.length);
        for (int i = 0; i < items.length; i ++) {
            int localMinimum = -1;
            for (int j = i; j < items.length; j ++) {
                keeper.performOperation(1);
                if (localMinimum < 0 || comparator.compare(items[j], items[localMinimum]) < 0) {
                    keeper.performOperation(1);
                    localMinimum = j;
                }
            }
            keeper.performOperation(1);
            ArrayUtils.swap(items, i, localMinimum);
        }
        keeper.releaseMemory(items.length);
        return keeper.done();
    }

}
