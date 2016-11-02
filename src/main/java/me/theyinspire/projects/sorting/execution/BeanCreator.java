package me.theyinspire.projects.sorting.execution;

import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:23 PM)
 */
public interface BeanCreator {

    BeanFactory getBeanFactory(List<BeanDefinition> dependencies);
}
