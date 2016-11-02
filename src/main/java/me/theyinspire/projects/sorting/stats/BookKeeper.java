package me.theyinspire.projects.sorting.stats;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:33 AM)
 */
public interface BookKeeper {

    void useMemory(int size);

    void releaseMemory(int size);

    void performOperation(int count);

    void reset();

    BookKeeperStatistics done();

}
