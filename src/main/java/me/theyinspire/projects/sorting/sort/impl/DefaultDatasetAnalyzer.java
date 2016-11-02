package me.theyinspire.projects.sorting.sort.impl;

import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.DataPoint;
import me.theyinspire.projects.sorting.stats.DataPointSelector;
import me.theyinspire.projects.sorting.stats.DatasetAnalyzer;
import me.theyinspire.projects.sorting.stats.impl.AverageBookKeeperStatistics;
import me.theyinspire.projects.sorting.tools.NumberUtils;
import me.theyinspire.projects.sorting.tools.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:34 PM)
 */
public class DefaultDatasetAnalyzer implements DatasetAnalyzer {

    @Override
    public <X extends Number, Y extends Number> Map<X, Map<String, Y>> analyze(Map<String, List<BookKeeperStatistics>> stats, DataPointSelector<X, Y> selector) {
        final Map<X, Map<String, Y>> result = new HashMap<>();
        final List<X> indicators = new ArrayList<>();
        final Map<String, Map<X, Y>> interim = new HashMap<>();
        final List<Pair<String, Map<X, Y>>> pairs = analyzeDataPoints(stats, selector);
        final Y zero = prepareData(indicators, interim, pairs);
        formResults(result, indicators, interim);
        fillBlanks(result, indicators, zero);
        return result;
    }

    private static <X extends Number, Y extends Number> void fillBlanks(Map<X, Map<String, Y>> result, List<X> indicators, Y zero) {
        Collections.sort(indicators, NumberUtils::compare);
        for (int i = 0; i < indicators.size(); i++) {
            final X x = indicators.get(i);
            final Map<String, Y> series = result.get(x);
            for (Map.Entry<String, Y> entry : series.entrySet()) {
                final Y y = entry.getValue();
                if (y == null) {
                    if (i == 0) {
                        series.put(entry.getKey(), zero);
                    } else if (i == indicators.size() - 1) {
                        final Y previous = result.get(indicators.get(i - 1)).get(entry.getKey());
                        series.put(entry.getKey(), previous);
                    } else {
                        final Y previous = result.get(indicators.get(i - 1)).get(entry.getKey());
                        final Y next = result.get(indicators.get(i + 1)).get(entry.getKey());
                        series.put(entry.getKey(), NumberUtils.divide(NumberUtils.add(previous, next), (Y) NumberUtils.cast(zero.getClass(), 2)));
                    }
                }
            }
        }
    }

    private static <X extends Number, Y extends Number> List<Pair<String, Map<X, Y>>> analyzeDataPoints(Map<String, List<BookKeeperStatistics>> stats, DataPointSelector<X, Y> selector) {
        return stats.entrySet().stream()
                    .map(entry -> new Pair<>(entry.getKey(), collectDataPoints(selector, entry)))
                    .map(pair -> new Pair<>(pair.getFirst(), dataPointListToMap(pair.getSecond())))
                    .collect(Collectors.toList());
    }

    private static <X extends Number, Y extends Number> void formResults(Map<X, Map<String, Y>> result, List<X> indicators, Map<String, Map<X, Y>> interim) {
        final List<Pair<X, Map<String, Y>>> list = indicators.stream()
                .map(x -> new Pair<>(x, collectValues(x, interim)))
                .collect(Collectors.toList());
        list.forEach(pair -> result.put(pair.getFirst(), pair.getSecond()));
    }

    private static <X extends Number, Y extends Number> Map<String, Y> collectValues(X x, Map<String, Map<X, Y>> interim) {
        final Map<String, Y> result = new HashMap<>();
        for (Map.Entry<String, Map<X, Y>> entry : interim.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getOrDefault(x, null));
        }
        return result;
    }

    private static <X extends Number, Y extends Number> Y prepareData(List<X> indicators, Map<String, Map<X, Y>> interim, List<Pair<String, Map<X, Y>>> pairs) {
        Y zero = null;
        final Set<X> xs = new HashSet<>();
        for (Pair<String, Map<X, Y>> pair : pairs) {
            interim.put(pair.getFirst(), pair.getSecond());
            xs.addAll(pair.getSecond().keySet());
            if (zero == null && !pair.getSecond().values().isEmpty()) {
                zero = NumberUtils.zero(pair.getSecond().values().iterator().next());
            }
        }
        indicators.addAll(xs);
        if (zero == null) {
            throw new IllegalStateException("No data collected");
        }
        return zero;
    }

    private static <X extends Number, Y extends Number> List<DataPoint<X, Y>> collectDataPoints(DataPointSelector<X, Y> selector, Map.Entry<String, List<BookKeeperStatistics>> entry) {
        return entry.getValue().stream()
                .map(statistics -> (AverageBookKeeperStatistics) statistics)
                .map(selector::select)
                .collect(Collectors.toList());
    }

    private static <X extends Number, Y extends Number> Map<X, Y> dataPointListToMap(List<DataPoint<X, Y>> list) {
        final Map<X, Y> result = new HashMap<>();
        for (DataPoint<X, Y> point : list) {
            result.put(point.getX(), point.getY());
        }
        return result;
    }

}
