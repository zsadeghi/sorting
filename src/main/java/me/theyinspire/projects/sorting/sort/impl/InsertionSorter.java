package me.theyinspire.projects.sorting.sort.impl;

import me.theyinspire.projects.sorting.sort.Sorter;
import me.theyinspire.projects.sorting.stats.BookKeeper;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.impl.DefaultBookKeeper;

import java.util.Comparator;

/**
 * <p>This class divides the functionality expected of the insertion sort mechanism into two pieces:</p>
 * <ol>
 *     <li>Finding where a single item should be placed compared to a portion of the whole array, and</li>
 *     <li>Doing something about the realized placement.</li>
 * </ol>
 *
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:35 AM)
 */
public class InsertionSorter<E extends Comparable<E>> implements Sorter<E> {

    private final Comparator<E> comparator;

    public InsertionSorter(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * This method finds where a given item should be placed according to the provided comparator by looking at all the items
     * in the specified portion of the input array of items
     * @param items         all the items
     * @param item          the item which we want to place
     * @param length        the length of the array which should be visited
     * @param comparator    the comparator
     * @param keeper        the book keeper
     * @return the position of the item compared to the array, which can range from {@literal 0} to {@literal length} (thus
     * indicating that the item is comparatively larger than all the other items).
     */
    private int findPlacement(E[] items, E item, int length, Comparator<E> comparator, BookKeeper keeper) {
        int placement = length;
        for (int j = 0; j < length; j ++) {
            keeper.performOperation(1);
            if (comparator.compare(items[j], item) > 0) {
                keeper.performOperation(1);
                placement = j;
                break;
            }
        }
        return placement;
    }

    /**
     * This method will sort all the items in the array one by one by individually {@link #findPlacement(Comparable[], Comparable, int, Comparator, BookKeeper) placing them.}
     * @param items    the items to be sorted
     */
    @Override
    public BookKeeperStatistics sort(E[] items) {
        final BookKeeper keeper = new DefaultBookKeeper(items.length, "InsertionSort");
        keeper.useMemory(items.length);
        for (int i = 1; i < items.length; i++) {
            E item = items[i];
            int placement = findPlacement(items, item, i, comparator, keeper);
            keeper.performOperation(i - placement);
            System.arraycopy(items, placement, items, placement + 1, i - placement);
            items[placement] = item;
        }
        keeper.releaseMemory(items.length);
        return keeper.done();
    }

}
