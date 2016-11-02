package me.theyinspire.projects.sorting.config.impl;

import me.theyinspire.projects.sorting.config.ConfigurationResolver;
import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.config.RawConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:49 PM)
 */
public class DefaultConfigurationResolver implements ConfigurationResolver {

    @Override
    public ExecutionConfiguration resolve(String path) throws IOException {
        final File configFile = new File(path);
        if (!configFile.exists()) {
            throw new IllegalStateException("Configuration file not found: " + path);
        }
        final Yaml yaml = new Yaml(new Constructor(RawConfiguration.class));
        final RawConfiguration rawConfiguration = (RawConfiguration) yaml.load(new FileReader(configFile));
        return rawConfiguration.compile();
    }

}
