package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:18 AM)
 */
public class ImmutableBookKeeperStatistics implements BookKeeperStatistics {

    private final long operations;
    private final long maximumMemory;
    private final long unreleasedMemory;
    private final long duration;

    public ImmutableBookKeeperStatistics(long operations, long maximumMemory, long unreleasedMemory, long duration) {
        this.operations = operations;
        this.maximumMemory = maximumMemory;
        this.unreleasedMemory = unreleasedMemory;
        this.duration = duration;
    }

    @Override
    public long operations() {
        return operations;
    }

    @Override
    public long maximumMemory() {
        return maximumMemory;
    }

    @Override
    public long unreleasedMemory() {
        return unreleasedMemory;
    }

    @Override
    public long duration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Operations: " + operations + "\nPeak Memory: " + maximumMemory + "\nUnreleased Memory: " + unreleasedMemory + "\nDuration: " + duration + "ms";
    }
}
