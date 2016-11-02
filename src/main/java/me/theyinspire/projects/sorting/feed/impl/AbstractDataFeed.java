package me.theyinspire.projects.sorting.feed.impl;

import me.theyinspire.projects.sorting.feed.DataFeed;
import me.theyinspire.projects.sorting.feed.Record;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:07 PM)
 */
public abstract class AbstractDataFeed<E extends Comparable<E>> implements DataFeed<E> {

    public AbstractDataFeed() {
        reset();
    }

    @Override
    public Iterator<Record<E>> iterator() {
        return new DataFeedIterator<E>(this);
    }

    @Override
    public void close() throws IOException {
    }

}
