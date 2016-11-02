package me.theyinspire.projects.sorting.feed;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:43 PM)
 */
public interface DataFeedReader<E extends Comparable<E>> {

    E[] read(DataFeed<E> feed);

}
