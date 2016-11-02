package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanCreator;
import me.theyinspire.projects.sorting.execution.BeanDependency;
import me.theyinspire.projects.sorting.execution.RawBean;
import me.theyinspire.projects.sorting.execution.api.Scope;

import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:26 PM)
 */
public class ImmutableRawBean implements RawBean {

    private final String name;
    private final Class<?> type;
    private final Scope scope;
    private final List<BeanDependency> dependencies;
    private final BeanCreator beanCreator;

    public ImmutableRawBean(String name, Class<?> type, Scope scope, List<BeanDependency> dependencies, BeanCreator beanCreator) {
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.dependencies = dependencies;
        this.beanCreator = beanCreator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public List<BeanDependency> getDependencies() {
        return dependencies;
    }

    @Override
    public BeanCreator getBeanCreator() {
        return beanCreator;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

}
