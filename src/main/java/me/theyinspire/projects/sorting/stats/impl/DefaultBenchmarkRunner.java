package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.measure.SortednessMeasurer;
import me.theyinspire.projects.sorting.sort.Sorter;
import me.theyinspire.projects.sorting.sort.impl.QuickSorter;
import me.theyinspire.projects.sorting.stats.BenchmarkRunner;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.Sampler;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:59 AM)
 */
@SuppressWarnings("unchecked")
public class DefaultBenchmarkRunner implements BenchmarkRunner {

    private final Collection<? extends Sorter> sorters;
    private final Sampler sampler;
    private final SortednessMeasurer measurer;
    private final int runs;

    public DefaultBenchmarkRunner(Collection<? extends Sorter> sorters, Sampler sampler, SortednessMeasurer measurer, int runs) {
        this.sorters = sorters;
        this.sampler = sampler;
        this.measurer = measurer;
        this.runs = runs;
    }

    @Override
    public Map<String, List<BookKeeperStatistics>> run(Comparable[] source) {
        final List<Comparable[]> samples = new ArrayList<>();
        final List<Double> sortedness = new ArrayList<>();
        samples.add(sampler.sample(source, source.length / 100));
        samples.add(sampler.sample(source, source.length / 50));
        samples.add(sampler.sample(source, source.length / 20));
        samples.add(sampler.sample(source, source.length / 10));
        samples.add(sampler.sample(source, source.length / 6));
        samples.add(sampler.sample(source, source.length / 5));
        samples.add(sampler.sample(source, source.length / 4));
        samples.add(sampler.sample(source, source.length / 3));
        samples.add(sampler.sample(source, source.length / 2));
        samples.add(sampler.sample(source, source.length));
        for (Comparable[] sample : samples) {
            sortedness.add(measurer.measure(sample));
        }
        final ExecutorService service = Executors.newFixedThreadPool(sorters.size() * 4);
        final Map<String, List<BookKeeperStatistics>> map = new HashMap<>();
        final Set<Future<BookKeeperStatistics>> futures = new HashSet<>();
        for (Sorter sorter : sorters) {
            for (int i = 0; i < samples.size(); i++) {
                final Comparable[] sample = samples.get(i);
                futures.add(service.submit(new SortBenchmark(sample, sorter, runs, sortedness.get(i))));
            }
        }
        for (Future<BookKeeperStatistics> future : futures) {
            try {
                final BookKeeperStatistics statistics = future.get();
                if (!map.containsKey(statistics.name())) {
                    map.put(statistics.name(), new ArrayList<>());
                }
                map.get(statistics.name()).add(statistics);
            } catch (Exception ignored) {
            }
        }
        service.shutdownNow();
        for (List<BookKeeperStatistics> list : map.values()) {
            Collections.sort(list, (first, second) -> Long.compare(first.size(), second.size()));
        }
        return map;
    }

}
