package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 12:38 AM)
 */
public class SingletonBeanFactory implements BeanFactory {

    private final BeanFactory delegate;
    private final AtomicReference<Object> bean;

    public SingletonBeanFactory(BeanFactory delegate) {
        this.delegate = delegate;
        bean = new AtomicReference<>();
    }

    @Override
    public Object getInstance() {
        synchronized (this) {
            if (bean.get() == null) {
                bean.set(delegate.getInstance());
            }
        }
        return bean.get();
    }
}
