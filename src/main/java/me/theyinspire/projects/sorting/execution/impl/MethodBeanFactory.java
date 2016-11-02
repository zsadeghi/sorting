package me.theyinspire.projects.sorting.execution.impl;

import me.theyinspire.projects.sorting.execution.BeanDefinition;
import me.theyinspire.projects.sorting.execution.BeanFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:18 PM)
 */
public class MethodBeanFactory implements BeanFactory {

    private final Method method;
    private final Object instance;
    private final List<BeanDefinition> dependencies;

    public MethodBeanFactory(Object instance, Method method, List<BeanDefinition> dependencies) {
        this.instance = instance;
        this.method = method;
        this.dependencies = dependencies;
        method.setAccessible(true);
    }

    @Override
    public Object getInstance() {
        final Object[] arguments = dependencies.stream().map(BeanDefinition::getBeanFactory).map(BeanFactory::getInstance).collect(Collectors.toList()).toArray();
        try {
            return method.invoke(instance, arguments);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to get bean instance", e);
        }
    }

}
