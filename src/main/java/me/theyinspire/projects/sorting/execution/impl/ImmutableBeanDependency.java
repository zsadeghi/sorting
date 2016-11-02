package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanDependency;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:29 PM)
 */
public class ImmutableBeanDependency implements BeanDependency {

    private final String name;
    private final Class<?> type;

    public ImmutableBeanDependency(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

}
