package me.theyinspire.projects.sorting.execution;

import java.util.List;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:22 PM)
 */
public interface RawBean {

    String getName();

    Class<?> getType();

    List<BeanDependency> getDependencies();

    BeanCreator getBeanCreator();

}
