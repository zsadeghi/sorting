package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanCreator;
import me.theyinspire.projects.sorting.execution.BeanDefinition;
import me.theyinspire.projects.sorting.execution.BeanFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:34 PM)
 */
public class DefaultBeanCreator implements BeanCreator {

    private final Method method;
    private final Object instance;

    public DefaultBeanCreator(Method method, Object instance) {
        this.method = method;
        method.setAccessible(true);
        this.instance = instance;
    }

    @Override
    public BeanFactory getBeanFactory(List<BeanDefinition> dependencies) {
        return new MethodBeanFactory(instance, method, dependencies);
    }

}
