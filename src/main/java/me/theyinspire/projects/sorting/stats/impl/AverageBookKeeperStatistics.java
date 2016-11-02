package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:55 AM)
 */
public class AverageBookKeeperStatistics implements BookKeeperStatistics {

    private final double sortedness;
    private long operations;
    private long maximumMemory;
    private long unreleasedMemory;
    private long duration;
    private long size;
    private String name;

    public AverageBookKeeperStatistics(double sortedness, BookKeeperStatistics... stats) {
        this.sortedness = sortedness;
        operations = 0L;
        maximumMemory = 0L;
        unreleasedMemory = 0L;
        duration = 0L;
        size = 0L;
        name = null;
        for (BookKeeperStatistics stat : stats) {
            if (name == null) {
                name = stat.name();
            }
            if (size == 0L) {
                size = stat.size();
            }
            operations += stat.operations();
            maximumMemory += stat.maximumMemory();
            unreleasedMemory += stat.unreleasedMemory();
            duration += stat.duration();
        }
        operations = operations / stats.length;
        maximumMemory = maximumMemory() / stats.length;
        unreleasedMemory = unreleasedMemory() / stats.length;
        duration = duration / stats.length;
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

    public double sortedness() {
        return sortedness;
    }

    @Override
    public String toString() {
        return "<n=" + size + ",op=" + operations + ",mem=" + maximumMemory + ",t=" + duration + "ms,sorted=" + sortedness + ">";
    }

}
