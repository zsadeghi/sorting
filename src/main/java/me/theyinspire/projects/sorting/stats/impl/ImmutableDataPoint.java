package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.stats.DataPoint;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:18 PM)
 */
public class ImmutableDataPoint<X extends Number, Y extends Number> implements DataPoint<X, Y> {

    private final X x;
    private final Y y;

    public ImmutableDataPoint(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public X getX() {
        return x;
    }

    @Override
    public Y getY() {
        return y;
    }

}
