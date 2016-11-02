package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanCreator;
import me.theyinspire.projects.sorting.execution.BeanDefinition;
import me.theyinspire.projects.sorting.execution.BeanFactory;

import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:37 PM)
 */
public class DelegateBeanCreator implements BeanCreator {

    private final Object bean;

    public DelegateBeanCreator(Object bean) {
        this.bean = bean;
    }

    @Override
    public BeanFactory getBeanFactory(List<BeanDefinition> dependencies) {
        return new DelegateBeanFactory(bean);
    }

}
