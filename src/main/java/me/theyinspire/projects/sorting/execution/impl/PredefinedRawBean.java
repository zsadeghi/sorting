package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanCreator;
import me.theyinspire.projects.sorting.execution.BeanDependency;
import me.theyinspire.projects.sorting.execution.RawBean;
import me.theyinspire.projects.sorting.execution.api.Scope;

import java.util.Collections;
import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:39 PM)
 */
public class PredefinedRawBean implements RawBean {

    private final Object bean;
    private final String name;

    public PredefinedRawBean(Object bean) {
        this.bean = bean;
        name = bean.getClass().getSimpleName().concat("#");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getType() {
        return bean.getClass();
    }

    @Override
    public List<BeanDependency> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public BeanCreator getBeanCreator() {
        return new DelegateBeanCreator(bean);
    }

    @Override
    public Scope getScope() {
        return Scope.SINGLETON;
    }
}
