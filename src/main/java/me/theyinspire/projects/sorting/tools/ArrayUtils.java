package me.theyinspire.projects.sorting.tools;

import java.util.Arrays;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:35 AM)
 */
public final class ArrayUtils {

    private ArrayUtils() {
        throw new UnsupportedOperationException();
    }

    public static void swap(Object[] array, int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <E> E[] concat(E[] left, E[] right) {
        final E[] result = Arrays.copyOf(left, left.length + right.length);
        System.arraycopy(right, 0, result, left.length, right.length);
        return result;
    }

}
