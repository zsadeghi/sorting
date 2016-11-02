package me.theyinspire.projects.sorting.stats;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:17 AM)
 */
public interface BookKeeperStatistics {

    long operations();

    long maximumMemory();

    long unreleasedMemory();

    long duration();

}
