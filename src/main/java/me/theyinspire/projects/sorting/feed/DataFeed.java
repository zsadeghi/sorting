package me.theyinspire.projects.sorting.feed;

import java.io.Closeable;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:02 PM)
 */
public interface DataFeed<E extends Comparable<E>> extends Iterable<Record<E>>, Closeable {

    Record<E> next();

    boolean hasNext();

    void reset();

}
