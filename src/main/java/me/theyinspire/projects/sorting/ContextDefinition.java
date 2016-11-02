package me.theyinspire.projects.sorting;

import me.theyinspire.projects.sorting.config.ConfigurationResolver;
import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.config.impl.DefaultConfigurationResolver;
import me.theyinspire.projects.sorting.execution.Environment;
import me.theyinspire.projects.sorting.execution.api.Bean;
import me.theyinspire.projects.sorting.feed.DataFeedReader;
import me.theyinspire.projects.sorting.feed.impl.LinkedListDataFeedReader;

import java.io.IOException;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:48 PM)
 */
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

}
