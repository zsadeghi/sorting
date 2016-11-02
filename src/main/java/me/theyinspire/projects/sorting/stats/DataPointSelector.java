package me.theyinspire.projects.sorting.stats;

import me.theyinspire.projects.sorting.stats.impl.AverageBookKeeperStatistics;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:18 PM)
 */
@FunctionalInterface
public interface DataPointSelector<X extends Number, Y extends Number> {

    DataPoint<X, Y> select(AverageBookKeeperStatistics statistics);

}
