package me.theyinspire.projects.sorting.measure;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 7:22 PM)
 */
public interface SortednessMeasurer {

    <E extends Comparable<E>> double measure(E[] input);

}
