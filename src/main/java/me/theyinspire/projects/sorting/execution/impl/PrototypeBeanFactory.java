package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanFactory;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:39 AM)
 */
public class PrototypeBeanFactory implements BeanFactory {

    private final BeanFactory delegate;

    public PrototypeBeanFactory(BeanFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object getInstance() {
        return delegate.getInstance();
    }

}
