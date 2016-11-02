package me.theyinspire.projects.sorting.config;

import me.theyinspire.projects.sorting.feed.DataFeed;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:36 PM)
 */
public class ExecutionConfiguration {

    private DataFeed<?> feed;

    public DataFeed<?> getFeed() {
        return feed;
    }

    public void setFeed(DataFeed<?> feed) {
        this.feed = feed;
    }

}