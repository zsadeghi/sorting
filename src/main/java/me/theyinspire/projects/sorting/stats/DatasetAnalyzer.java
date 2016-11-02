package me.theyinspire.projects.sorting.stats;

import java.util.List;
import java.util.Map;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:24 PM)
 */
public interface DatasetAnalyzer {

    <X extends Number, Y extends Number> Map<X, Map<String, Y>> analyze(Map<String, List<BookKeeperStatistics>> stats, DataPointSelector<X, Y> selector);

}
