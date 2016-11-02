package me.theyinspire.projects.sorting;

import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.execution.ApplicationContext;
import me.theyinspire.projects.sorting.execution.impl.DefaultApplicationContext;
import me.theyinspire.projects.sorting.feed.DataFeedReader;
import me.theyinspire.projects.sorting.measure.SortednessMeasurer;
import me.theyinspire.projects.sorting.sort.Sorter;
import me.theyinspire.projects.sorting.stats.BookKeeperStatistics;

import java.util.Collection;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:02 PM)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        final ApplicationContext context = new DefaultApplicationContext(ContextDefinition.class);
        final ExecutionConfiguration configuration = context.getBean(ExecutionConfiguration.class);
        final Comparable[] values = context.getBean(DataFeedReader.class).read(configuration.getFeed());
        System.out.println("values.length = " + values.length);
        final double measure = context.getBean(SortednessMeasurer.class).measure(values);
        System.out.println("measure = " + measure);
        System.out.println("sorted = " + measure * values.length);
        final Collection<Sorter> sorters = context.getBeansOfType(Sorter.class).values();
        System.out.println();
        for (Sorter sorter : sorters) {
            final String simpleName = sorter.getClass().getSimpleName();
            System.out.println(simpleName);
            System.out.println(new String(new char[simpleName.length()]).replace('\0', '='));
            final BookKeeperStatistics statistics = sorter.sort(values);
            System.out.println(statistics);
            System.out.println();
        }
    }

}
