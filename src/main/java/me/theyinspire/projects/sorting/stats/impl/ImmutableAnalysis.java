package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.stats.Analysis;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.DataPointSelector;

import java.util.List;
import java.util.Map;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:51 PM)
 */
public class ImmutableAnalysis<X extends Number, Y extends Number> implements Analysis<X, Y> {

    private final String name;
    private final String measure;
    private final String parameter;
    private final DataPointSelector<X, Y> selector;
    private final Map<String, List<BookKeeperStatistics>> dataset;

    public ImmutableAnalysis(String name, String parameter, String measure, Map<String, List<BookKeeperStatistics>> dataset, DataPointSelector<X, Y> selector) {
        this.name = name;
        this.measure = measure;
        this.parameter = parameter;
        this.selector = selector;
        this.dataset = dataset;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMeasure() {
        return measure;
    }

    @Override
    public String getParameter() {
        return parameter;
    }

    @Override
    public DataPointSelector<X, Y> getSelector() {
        return selector;
    }

    @Override
    public Map<String, List<BookKeeperStatistics>> getDataset() {
        return dataset;
    }
}
