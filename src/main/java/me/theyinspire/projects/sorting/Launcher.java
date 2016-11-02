package me.theyinspire.projects.sorting;

import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.execution.ApplicationContext;
import me.theyinspire.projects.sorting.execution.impl.ConfigurableEnvironment;
import me.theyinspire.projects.sorting.execution.impl.DefaultApplicationContext;
import me.theyinspire.projects.sorting.feed.DataFeedReader;
import me.theyinspire.projects.sorting.stats.BenchmarkRunner;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;
import me.theyinspire.projects.sorting.stats.impl.AverageBookKeeperStatistics;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:02 PM)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        System.out.println("Suit 1");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        benchmark("storm");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println();
        System.out.println("Suit 2");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        benchmark("housing");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println();
        System.out.println("Suit 3");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        benchmark("synth1");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println();
        System.out.println("Suit 4");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        benchmark("synth2");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    private static void benchmark(String file) throws Exception {
        final ConfigurableEnvironment environment = new ConfigurableEnvironment();
        environment.setProperty("config", "/Users/milad/Projects/Java/sorting/src/main/resources/config/" + file + ".yaml");
        final ApplicationContext context = new DefaultApplicationContext(ContextDefinition.class, environment);
        final ExecutionConfiguration configuration = context.getBean(ExecutionConfiguration.class);
        //noinspection unchecked
        final Comparable[] values = context.getBean(DataFeedReader.class).read(configuration.getFeed());
        final BenchmarkRunner runner = context.getBean(BenchmarkRunner.class);
        final Map<String, List<BookKeeperStatistics>> results = runner.run(values);
        final String base = "/Users/milad/Projects/Java/sorting/src/main/resources/reports/" + file;
        final List<String> algorithms = results.keySet().stream().sorted().collect(Collectors.toList());
        printMemoryBySize(results, base, algorithms);
        printDurationBySize(results, base, algorithms);
        printMemoryBySortedness(results, base, algorithms);
        printDurationBySortedness(results, base, algorithms);
    }

    private static void printDurationBySortedness(Map<String, List<BookKeeperStatistics>> results, String base, List<String> algorithms) throws IOException {
        final List<String> headers = new ArrayList<>(algorithms);
        headers.add(0, "Sortedness");
        final CSVPrinter printer = CSVFormat.EXCEL.withHeader(headers.toArray(new String[headers.size()])).print(new FileWriter(base + "-time-sortedness.csv"));
        final List<Double> sortedness = results.values().stream().flatMap(Collection::stream).map(x -> (AverageBookKeeperStatistics) x).map(AverageBookKeeperStatistics::sortedness).distinct().sorted().collect(Collectors.toList());
        for (Double value : sortedness) {
            printer.print(value);
            for (String algorithm : algorithms) {
                printer.print(results.get(algorithm).stream().filter(bookKeeperStatistics -> ((AverageBookKeeperStatistics) bookKeeperStatistics).sortedness() == value).findFirst().map(x -> x.duration() * 1000. / x.size()).orElse(0D));
            }
            printer.println();
            printer.flush();
        }
        printer.close();
    }

    private static void printMemoryBySortedness(Map<String, List<BookKeeperStatistics>> results, String base, List<String> algorithms) throws IOException {
        final List<String> headers = new ArrayList<>(algorithms);
        headers.add(0, "Sortedness");
        final CSVPrinter printer = CSVFormat.EXCEL.withHeader(headers.toArray(new String[headers.size()])).print(new FileWriter(base + "-memory-sortedness.csv"));
        final List<Double> sortedness = results.values().stream().flatMap(Collection::stream).map(x -> (AverageBookKeeperStatistics) x).map(AverageBookKeeperStatistics::sortedness).distinct().sorted().collect(Collectors.toList());
        for (Double value : sortedness) {
            printer.print(value);
            for (String algorithm : algorithms) {
                printer.print(results.get(algorithm).stream().filter(bookKeeperStatistics -> ((AverageBookKeeperStatistics) bookKeeperStatistics).sortedness() == value).findFirst().map(x -> x.maximumMemory() * 1000. / x.size()).orElse(0D));
            }
            printer.println();
            printer.flush();
        }
        printer.close();
    }

    private static void printDurationBySize(Map<String, List<BookKeeperStatistics>> results, String base, List<String> algorithms) throws IOException {
        final List<String> headers = new ArrayList<>(algorithms);
        headers.add(0, "Size");
        final CSVPrinter printer = CSVFormat.EXCEL.withHeader(headers.toArray(new String[headers.size()])).print(new FileWriter(base + "-time-size.csv"));
        final List<Long> sizes = results.values().stream().flatMap(Collection::stream).map(BookKeeperStatistics::size).distinct().sorted().collect(Collectors.toList());
        for (Long size : sizes) {
            printer.print(size);
            for (String algorithm : algorithms) {
                final Long duration = results.get(algorithm).stream().filter(bookKeeperStatistics -> bookKeeperStatistics.size() == size).findFirst().map(BookKeeperStatistics::duration).orElse(0L);
                printer.print(duration);
            }
            printer.println();
            printer.flush();
        }
        printer.close();
    }

    private static void printMemoryBySize(Map<String, List<BookKeeperStatistics>> results, String base, List<String> algorithms) throws IOException {
        final List<String> headers = new ArrayList<>(algorithms);
        headers.add(0, "Size");
        final CSVPrinter printer = CSVFormat.EXCEL.withHeader(headers.toArray(new String[headers.size()])).print(new FileWriter(base + "-memory-size.csv"));
        final List<Long> sizes = results.values().stream().flatMap(Collection::stream).map(BookKeeperStatistics::size).distinct().sorted().collect(Collectors.toList());
        for (Long size : sizes) {
            printer.print(size);
            for (String algorithm : algorithms) {
                final Long memory = results.get(algorithm).stream().filter(bookKeeperStatistics -> bookKeeperStatistics.size() == size).findFirst().map(BookKeeperStatistics::maximumMemory).orElse(0L);
                printer.print(memory);
            }
            printer.println();
            printer.flush();
        }
        printer.close();
    }

}
