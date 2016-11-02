package me.theyinspire.projects.sorting.execution;

import java.util.Map;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 11:01 PM)
 */
public interface ApplicationContext {

    <E> Map<String, E> getBeansOfType(Class<E> type);

    <E> E getBean(Class<E> type);

    <E> E getBean(Class<E> type, String name);
}
