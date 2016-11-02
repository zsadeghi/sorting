package me.theyinspire.projects.sorting.feed.impl;

import me.theyinspire.projects.sorting.feed.Record;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:05 PM)
 */
public class ImmutableRecord<E extends Comparable<E>> implements Record<E> {

    private final long index;
    private final E value;

    public ImmutableRecord(long index, E value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public long getIndex() {
        return index;
    }

    @Override
    public E getValue() {
        return value;
    }

    @Override
    public int compareTo(Record<E> o) {
        return value == null && o.getValue() == null ? 0 : (value == null ? -1 : 1);
    }

}
