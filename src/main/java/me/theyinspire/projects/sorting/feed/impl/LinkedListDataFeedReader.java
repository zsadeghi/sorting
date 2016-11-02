package me.theyinspire.projects.sorting.feed.impl;

import me.theyinspire.projects.sorting.feed.DataFeed;
import me.theyinspire.projects.sorting.feed.DataFeedReader;
import me.theyinspire.projects.sorting.feed.Record;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:44 PM)
 */
public class LinkedListDataFeedReader<E extends Comparable<E>> implements DataFeedReader<E> {

    @SuppressWarnings("unchecked")
    @Override
    public E[] read(DataFeed<E> feed) {
        final List<E> list = new LinkedList<>();
        feed.reset();
        for (Record<E> record : feed) {
            list.add(record.getValue());
        }
        if (list.isEmpty()) {
            return (E[]) new Comparable[0];
        } else {
            final Class<? extends Comparable> componentType = list.get(0).getClass();
            final E[] array = (E[]) Array.newInstance(componentType, list.size());
            return list.toArray(array);
        }
    }

}
