package me.theyinspire.projects.sorting.config;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:48 PM)
 */
public interface ConfigurationResolver {

    ExecutionConfiguration resolve(String path) throws IOException;

}
