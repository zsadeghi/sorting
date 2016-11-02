package me.theyinspire.projects.sorting.stats;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:49 AM)
 */
public interface Sampler {

    <E> E[] sample(E[] original, int size);

}
