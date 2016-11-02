package me.theyinspire.projects.sorting;

import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.execution.ApplicationContext;
import me.theyinspire.projects.sorting.execution.impl.ConfigurableEnvironment;
import me.theyinspire.projects.sorting.execution.impl.DefaultApplicationContext;
import me.theyinspire.projects.sorting.feed.DataFeedReader;
import me.theyinspire.projects.sorting.stats.AnalysisWriter;
import me.theyinspire.projects.sorting.stats.BenchmarkRunner;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.impl.ImmutableAnalysis;
import me.theyinspire.projects.sorting.stats.impl.ImmutableDataPoint;

import java.util.List;
import java.util.Map;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:02 PM)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        benchmark("storm");
        benchmark("housing");
        benchmark("synth1");
        benchmark("synth2");
    }

    private static void benchmark(String file) throws Exception {
        final ConfigurableEnvironment environment = new ConfigurableEnvironment();
        environment.setProperty("config", "src/main/resources/config/" + file + ".yaml");
        final ApplicationContext context = new DefaultApplicationContext(ContextDefinition.class, environment);
        final ExecutionConfiguration configuration = context.getBean(ExecutionConfiguration.class);
        //noinspection unchecked
        final Comparable[] values = context.getBean(DataFeedReader.class).read(configuration.getFeed());
        final BenchmarkRunner runner = context.getBean(BenchmarkRunner.class);
        final Map<String, List<BookKeeperStatistics>> results = runner.run(values);
        final AnalysisWriter writer = context.getBean(AnalysisWriter.class);
        writer.write(new ImmutableAnalysis<>(file, "sortedness", "time", results, statistics -> new ImmutableDataPoint<>(statistics.sortedness(), statistics.duration())));
        writer.write(new ImmutableAnalysis<>(file, "size", "time", results, statistics -> new ImmutableDataPoint<>(statistics.size(), statistics.duration())));
        writer.write(new ImmutableAnalysis<>(file, "sortedness", "memory", results, statistics -> new ImmutableDataPoint<>(statistics.sortedness(), statistics.maximumMemory())));
        writer.write(new ImmutableAnalysis<>(file, "size", "memory", results, statistics -> new ImmutableDataPoint<>(statistics.size(), statistics.maximumMemory())));
    }

}
