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
    private final long size;
    private final String name;

    public ImmutableBookKeeperStatistics(long operations, long maximumMemory, long unreleasedMemory, long duration, long size, String name) {
        this.operations = operations;
        this.maximumMemory = maximumMemory;
        this.unreleasedMemory = unreleasedMemory;
        this.duration = duration;
        this.size = size;
        this.name = name;
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
    public long size() {
        return size;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "<n=" + size + ",op=" + operations + ",mem=" + maximumMemory + ",t=" + duration + "ms>";
    }
}
