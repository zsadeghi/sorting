package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.stats.BookKeeper;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:35 AM)
 */
public class DefaultBookKeeper implements BookKeeper {

    private long memory;
    private long maximumMemory;
    private long operations;
    private long start;
    private boolean done;
    private final long size;
    private final String name;

    public DefaultBookKeeper(long size, String name) {
        this.size = size;
        this.name = name;
        memory = 0L;
        maximumMemory = 0L;
        operations = 0L;
        start = System.currentTimeMillis();
        done = false;
    }

    private void checkDone() {
        if (done) {
            throw new IllegalStateException("BookKeeper has already finished recording this job");
        }
    }

    @Override
    public void useMemory(int size) {
        checkDone();
        memory += size;
        if (memory > maximumMemory) {
            maximumMemory = memory;
        }
    }

    @Override
    public void releaseMemory(int size) {
        checkDone();
        memory = Math.max(0L, memory - size);
    }

    @Override
    public void performOperation(int count) {
        checkDone();
        operations += Math.max(0, count);
    }

    @Override
    public void reset() {
        done = false;
        operations = 0L;
        memory = 0L;
        maximumMemory = 0L;
    }

    @Override
    public BookKeeperStatistics done() {
        done = true;
        return new ImmutableBookKeeperStatistics(operations, maximumMemory, memory, System.currentTimeMillis() - start, size, name);
    }

}
