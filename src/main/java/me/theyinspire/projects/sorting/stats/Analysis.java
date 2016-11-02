package me.theyinspire.projects.sorting.stats;

import java.util.List;
import java.util.Map;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:46 PM)
 */
public interface Analysis<X extends Number, Y extends Number> {

    String getMeasure();

    String getParameter();

    String getName();

    DataPointSelector<X, Y> getSelector();

    Map<String, List<BookKeeperStatistics>> getDataset();

}
