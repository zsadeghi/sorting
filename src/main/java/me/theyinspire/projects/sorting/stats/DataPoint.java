package me.theyinspire.projects.sorting.stats;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:17 PM)
 */
public interface DataPoint<X extends Number, Y extends Number> {

    X getX();

    Y getY();

}
