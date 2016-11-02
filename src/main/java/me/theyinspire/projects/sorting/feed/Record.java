package me.theyinspire.projects.sorting.feed;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:04 PM)
 */
public interface Record<E extends Comparable<E>> extends Comparable<Record<E>> {

    long getIndex();

    E getValue();

}
