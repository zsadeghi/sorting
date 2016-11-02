package me.theyinspire.projects.sorting.feed.impl;

import me.theyinspire.projects.sorting.feed.Record;

import java.util.Random;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:08 PM)
 */
public class RandomNumberDataFeed extends AbstractDataFeed<Integer> {
    
    private Random random;
    private final Integer maximum;
    private final Integer size;
    private Integer current;

    public RandomNumberDataFeed(Integer maximum, Integer size) {
        this.maximum = maximum;
        this.size = size;
        reset();
    }

    @Override
    public Record<Integer> next() {
        return hasNext() ? new ImmutableRecord<>(current ++, random.nextInt(maximum)) : null;
    }

    @Override
    public boolean hasNext() {
        return current < size;
    }

    @Override
    public void reset() {
        current = 0;
        random = new Random();
    }

}
