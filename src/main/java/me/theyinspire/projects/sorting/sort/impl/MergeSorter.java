package me.theyinspire.projects.sorting.sort.impl;

import me.theyinspire.projects.sorting.sort.Sorter;
import me.theyinspire.projects.sorting.stats.BookKeeper;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.impl.DefaultBookKeeper;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:35 AM)
 */
public class MergeSorter<E extends Comparable<E>> implements Sorter<E> {

    private final Comparator<E> comparator;

    public MergeSorter(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * This method merges the two halves of an array, given that each half is sorted individually
     * @param array    the array
     * @param from     the starting point of the array
     * @param mid      the middle of the array
     * @param to       the end of the array
     * @param keeper   the book keeper
     */
    @SuppressWarnings("unchecked")
    private void merge(E[] array, int from, int mid, int to, BookKeeper keeper) {
        //let's create two arrays for the left and right portions of the original array
        keeper.useMemory(1);
        keeper.useMemory(to - from);
        final E[] left = (E[]) Array.newInstance(array.getClass().getComponentType(), mid - from);
        final E[] right = (E[]) Array.newInstance(array.getClass().getComponentType(), to - mid);
        keeper.performOperation(left.length);
        keeper.performOperation(right.length);
        System.arraycopy(array, from, left, 0, left.length);
        System.arraycopy(array, mid, right, 0, right.length);
        //we need two cursors, each of which are pointing at the current point of interest in either array
        int leftCursor = 0;
        int rightCursor = 0;
        //we need a cursor which points to the point at which the winning item should be written
        int cursor = from;
        while (cursor < to) {
            keeper.performOperation(1);
            final E leftItem;
            final E rightItem;
            if (leftCursor < left.length) {
                leftItem = left[leftCursor];
            } else {
                leftItem = null;
            }
            keeper.performOperation(1);
            if (rightCursor < right.length) {
                rightItem = right[rightCursor];
            } else {
                rightItem = null;
            }
            keeper.performOperation(1);
            if (leftItem == null) {
                array[cursor] = rightItem;
                rightCursor ++;
            } else if (rightItem == null) {
                array[cursor] = leftItem;
                leftCursor ++;
            } else if (comparator.compare(rightItem, leftItem) < 0) {
                array[cursor] = rightItem;
                rightCursor ++;
            } else {
                array[cursor] = leftItem;
                leftCursor ++;
            }
            cursor ++;
        }
        keeper.releaseMemory(to - from);
        keeper.releaseMemory(1);
    }

    /**
     * This method sorts the indicated portion of the array by first sorting its two halves, and then merging the sorted havles
     * using {@link #merge(Comparable[], int, int, int, BookKeeper)}
     * @param items    the array
     * @param from     the beginning of the target portion
     * @param to       the end of the target portion
     * @param keeper   the book keeper
     */
    private void sort(E[] items, int from, int to, BookKeeper keeper) {
        keeper.useMemory(1);
        keeper.performOperation(1);
        if (to - from < 2) {
            return;
        }
        keeper.performOperation(1);
        int mid = from + (to - from) / 2;
        sort(items, from, mid, keeper);
        sort(items, mid, to, keeper);
        merge(items, from, mid, to, keeper);
        keeper.releaseMemory(1);
    }

    @Override
    public final BookKeeperStatistics sort(E[] items) {
        final BookKeeper keeper = new DefaultBookKeeper();
        keeper.useMemory(items.length);
        sort(items, 0, items.length, keeper);
        keeper.releaseMemory(items.length);
        return keeper.done();
    }

}
