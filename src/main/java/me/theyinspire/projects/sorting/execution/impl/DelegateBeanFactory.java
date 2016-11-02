package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanFactory;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:56 PM)
 */
public class DelegateBeanFactory implements BeanFactory {

    private final Object bean;

    public DelegateBeanFactory(Object bean) {
        this.bean = bean;
    }

    @Override
    public Object getInstance() {
        return bean;
    }
}
