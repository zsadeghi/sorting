package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanDefinition;
import me.theyinspire.projects.sorting.execution.BeanFactory;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:53 PM)
 */
public class ImmutableBeanDefinition implements BeanDefinition {


    private final String name;
    private final Class<?> type;
    private final BeanFactory beanFactory;

    public ImmutableBeanDefinition(String name, Class<?> type, BeanFactory beanFactory) {
        this.name = name;
        this.type = type;
        this.beanFactory = beanFactory;
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
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

}
