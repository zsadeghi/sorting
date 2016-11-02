package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.stats.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:39 PM)
 */
public class DefaultAnalysisWriter implements AnalysisWriter {

    private final DatasetAnalyzer analyzer;
    private final ExecutionConfiguration configuration;

    public DefaultAnalysisWriter(DatasetAnalyzer analyzer, ExecutionConfiguration configuration) {
        this.analyzer = analyzer;
        this.configuration = configuration;
    }

    @Override
    public <X extends Number, Y extends Number> void write(Analysis<X, Y> analysis) {
        final Map<String, List<BookKeeperStatistics>> stats = analysis.getDataset();
        final DataPointSelector<X, Y> selector = analysis.getSelector();
        final Map<X, Map<String, Y>> map = analyzer.analyze(stats, selector);
        final String filename = analysis.getName() + "-" + analysis.getMeasure() + "-by-" + analysis.getParameter() + ".csv";
        final List<String> algorithms = stats.keySet().stream().sorted().collect(Collectors.toList());
        final List<String> headers = new ArrayList<>(algorithms);
        headers.add(0, analysis.getParameter().substring(0, 1).toUpperCase().concat(analysis.getParameter().substring(1)));
        final CSVPrinter printer;
        try {
            printer = CSVFormat.EXCEL.withHeader(headers.toArray(new String[headers.size()])).print(new FileWriter(configuration.getOutput() + "/" + filename.toLowerCase()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        map.keySet().stream().sorted().forEach(row -> {
            try {
                printer.print(row);
                for (String algorithm : algorithms) {
                    printer.print(map.get(row).get(algorithm));
                }
                printer.println();
                printer.flush();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });
        try {
            printer.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
