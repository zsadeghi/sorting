package me.theyinspire.projects.sorting;

import me.theyinspire.projects.sorting.config.ConfigurationResolver;
import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.config.impl.DefaultConfigurationResolver;
import me.theyinspire.projects.sorting.execution.ApplicationContext;
import me.theyinspire.projects.sorting.execution.Environment;
import me.theyinspire.projects.sorting.execution.api.Bean;
import me.theyinspire.projects.sorting.feed.DataFeedReader;
import me.theyinspire.projects.sorting.feed.impl.LinkedListDataFeedReader;
import me.theyinspire.projects.sorting.measure.SortednessMeasurer;
import me.theyinspire.projects.sorting.measure.impl.MemoizingSortednessMeasurer;
import me.theyinspire.projects.sorting.sort.Sorter;
import me.theyinspire.projects.sorting.sort.impl.*;
import me.theyinspire.projects.sorting.stats.AnalysisWriter;
import me.theyinspire.projects.sorting.stats.BenchmarkRunner;
import me.theyinspire.projects.sorting.stats.DatasetAnalyzer;
import me.theyinspire.projects.sorting.stats.Sampler;
import me.theyinspire.projects.sorting.stats.impl.DefaultAnalysisWriter;
import me.theyinspire.projects.sorting.stats.impl.DefaultBenchmarkRunner;
import me.theyinspire.projects.sorting.stats.impl.DefaultSampler;

import java.io.IOException;
import java.util.Comparator;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:48 PM)
 */
@SuppressWarnings({"WeakerAccess", "unchecked"})
public class ContextDefinition {

    @Bean
    public ConfigurationResolver configurationResolver() {
        return new DefaultConfigurationResolver();
    }

    @Bean
    public DataFeedReader dataFeedReader() {
        return new LinkedListDataFeedReader();
    }

    @Bean
    public ExecutionConfiguration configuration(ConfigurationResolver resolver, Environment environment) throws IOException {
        return resolver.resolve(environment.getProperty("config"));
    }

    @Bean
    public SortednessMeasurer sortednessMeasurer() {
        return new MemoizingSortednessMeasurer();
    }

    @Bean
    public InsertionSorter insertionSorter() {
        return new InsertionSorter(Comparator.naturalOrder());
    }

    @Bean
    public SelectionSorter selectionSorter() {
        return new SelectionSorter(Comparator.naturalOrder());
    }

    @Bean
    public BubbleSorter bubbleSorter() {
        return new BubbleSorter(Comparator.naturalOrder());
    }

    @Bean
    public MergeSorter mergeSorter() {
        return new MergeSorter(Comparator.naturalOrder());
    }

    @Bean
    public QuickSorter quickSorter() {
        return new QuickSorter(Comparator.naturalOrder());
    }

    @Bean
    public Sampler sampler() {
        return new DefaultSampler();
    }

    @Bean
    public BenchmarkRunner benchmarkRunner(ApplicationContext context, Sampler sampler, ExecutionConfiguration configuration, SortednessMeasurer measurer) {
        return new DefaultBenchmarkRunner(context.getBeansOfType(Sorter.class).values(), sampler, measurer, configuration.getRuns());
    }

    @Bean
    public DatasetAnalyzer datasetAnalyzer() {
        return new DefaultDatasetAnalyzer();
    }

    @Bean
    public AnalysisWriter analysisWriter(DatasetAnalyzer analyzer, ExecutionConfiguration configuration) {
        return new DefaultAnalysisWriter(analyzer, configuration);
    }

}
