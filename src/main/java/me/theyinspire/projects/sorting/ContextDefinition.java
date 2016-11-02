package me.theyinspire.projects.sorting;

import me.theyinspire.projects.sorting.config.ConfigurationResolver;
import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.config.impl.DefaultConfigurationResolver;
import me.theyinspire.projects.sorting.execution.Environment;
import me.theyinspire.projects.sorting.execution.api.Bean;
import me.theyinspire.projects.sorting.feed.DataFeedReader;
import me.theyinspire.projects.sorting.feed.impl.LinkedListDataFeedReader;
import me.theyinspire.projects.sorting.measure.SortednessMeasurer;
import me.theyinspire.projects.sorting.measure.impl.MemoizingSortednessMeasurer;
import me.theyinspire.projects.sorting.sort.impl.*;

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

}
