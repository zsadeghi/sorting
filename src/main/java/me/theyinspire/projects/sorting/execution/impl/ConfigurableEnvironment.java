package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:58 PM)
 */
public class ConfigurableEnvironment implements Environment {

    private final Map<String, String> properties = new HashMap<>();

    public void setProperty(String name, String value) {
        properties.put(name, value);
    }

    @Override
    public String getProperty(String name) {
        return properties.get(name);
    }

}
