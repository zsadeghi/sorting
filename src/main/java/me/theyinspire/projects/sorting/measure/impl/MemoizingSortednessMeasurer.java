package me.theyinspire.projects.sorting.measure.impl;

import me.theyinspire.projects.sorting.measure.SortednessMeasurer;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 7:23 PM)
 */
public class MemoizingSortednessMeasurer<E extends Comparable<E>> implements SortednessMeasurer<E> {

    public double measure(E[] input) {
        int inputLength = input.length;
        int[] endings = new int[inputLength + 1];
        int length = 0;
        for (int i = 0; i <= inputLength - 1; i++) {
            int low = 1;
            int high = length;
            while (low <= high) {
                final int mid = (int) Math.round(Math.ceil((low + high) / 2.0));
                if (input[endings[mid]].compareTo(input[i]) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            final int extension = low;
            endings[extension] = i;
            if (extension > length) {
                length = extension;
            }
        }
        return (double) length / inputLength;
    }

}
