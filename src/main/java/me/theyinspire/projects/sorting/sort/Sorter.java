package me.theyinspire.projects.sorting.sort;

import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;

/**
 * This interface represents the common problem of sorting a number of items with the only assumption being that
 * some sort of comparison can be made between individual items of the array
 *
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:35 AM)
 */
public interface Sorter<E extends Comparable<E>> {

    /**
     * @param items    the items to be sorted
     */
    BookKeeperStatistics sort(E[] items);

}
