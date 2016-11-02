package me.theyinspire.projects.sorting.feed.impl;

import me.theyinspire.projects.sorting.feed.DataFeed;
import me.theyinspire.projects.sorting.feed.Record;

import java.util.Iterator;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:07 PM)
 */
class DataFeedIterator<E extends Comparable<E>> implements Iterator<Record<E>> {

    private final DataFeed<E> feed;

    DataFeedIterator(DataFeed<E> feed) {
        this.feed = feed;
    }

    @Override
    public boolean hasNext() {
        return feed.hasNext();
    }

    @Override
    public Record<E> next() {
        return feed.next();
    }

}
