package me.theyinspire.projects.sorting.stats;

import java.util.List;
import java.util.Map;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:51 AM)
 */
public interface BenchmarkRunner {

    Map<String, List<BookKeeperStatistics>> run(Comparable[] source);

}
