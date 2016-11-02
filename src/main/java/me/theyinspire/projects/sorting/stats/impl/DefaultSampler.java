package me.theyinspire.projects.sorting.stats.impl;

import me.theyinspire.projects.sorting.stats.Sampler;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:49 AM)
 */
public class DefaultSampler implements Sampler {

    @Override
    public <E> E[] sample(E[] original, int size) {
        //noinspection unchecked
        final E[] array = (E[]) Array.newInstance(original.getClass().getComponentType(), size);
        return Arrays.stream(original).limit(size).toArray(value -> array);
    }

}
