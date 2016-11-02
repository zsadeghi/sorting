package me.theyinspire.projects.sorting.execution;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:03 PM)
 */
public interface BeanDefinition {

    Class<?> getType();

    String getName();

    BeanFactory getBeanFactory();

}
