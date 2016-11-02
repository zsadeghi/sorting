package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.sort.Sorter;
import me.theyinspire.projects.sorting.stats.Benchmark;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 2:01 AM)
 */
public class SortBenchmark<E extends Comparable<E>> implements Benchmark {

    private final E[] source;
    private final Sorter<E> sorter;
    private final int runs;
    private final Double sortedness;

    public SortBenchmark(E[] source, Sorter<E> sorter, int runs, Double sortedness) {
        this.source = source;
        this.sorter = sorter;
        this.runs = runs;
        this.sortedness = sortedness;
    }

    @Override
    public BookKeeperStatistics call() throws Exception {
        final List<BookKeeperStatistics> list = new ArrayList<>();
        for (int i = 0; i < runs; i++) {
            list.add(sorter.sort(source));
        }
        return new AverageBookKeeperStatistics(sortedness, list.toArray(new BookKeeperStatistics[list.size()]));
    }

}
