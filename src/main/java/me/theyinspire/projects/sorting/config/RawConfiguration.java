package me.theyinspire.projects.sorting.config;

import me.theyinspire.projects.sorting.config.raw.FeedConfiguration;
import me.theyinspire.projects.sorting.feed.DataFeed;
import me.theyinspire.projects.sorting.feed.impl.CsvColumnType;
import me.theyinspire.projects.sorting.feed.impl.CsvDataFeed;
import me.theyinspire.projects.sorting.feed.impl.RandomNumberDataFeed;

import java.io.IOException;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:27 PM)
 */
public class RawConfiguration {

    private FeedConfiguration feed;
    private Integer runs = 3;

    public FeedConfiguration getFeed() {
        return feed;
    }

    public void setFeed(FeedConfiguration feed) {
        this.feed = feed;
    }
    
    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public ExecutionConfiguration compile() throws IOException {
        final ExecutionConfiguration configuration = new ExecutionConfiguration();
        final DataFeed<?> feed;
        if ("random".equalsIgnoreCase(getFeed().getType())) {
            feed = new RandomNumberDataFeed(getFeed().getBound(), getFeed().getSize());
        } else if ("csv".equalsIgnoreCase(getFeed().getType())) {
            final CsvColumnType<?> columnType;
            if ("string".equalsIgnoreCase(getFeed().getColumnType())) {
                columnType = CsvColumnType.STRING;
            } else if ("integer".equalsIgnoreCase(getFeed().getColumnType()) || "int".equalsIgnoreCase(getFeed().getColumnType())) {
                columnType = CsvColumnType.INTEGER;
            } else if ("long".equalsIgnoreCase(getFeed().getColumnType())) {
                columnType = CsvColumnType.LONG;
            } else {
                throw new IllegalArgumentException("Unknown column type: " + getFeed().getColumnType());
            }
            feed = new CsvDataFeed<>(columnType, getFeed().getColumn(), getFeed().getPath(), getFeed().getIgnoreFirst());
        } else {
            throw new IllegalStateException("Unsupported feed type: " + getFeed());
        }
        configuration.setFeed(feed);
        configuration.setRuns(runs);
        return configuration;
    }
}
