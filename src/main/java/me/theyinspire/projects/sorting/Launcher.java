package me.theyinspire.projects.sorting;

import me.theyinspire.projects.sorting.config.ExecutionConfiguration;
import me.theyinspire.projects.sorting.execution.ApplicationContext;
import me.theyinspire.projects.sorting.execution.impl.DefaultApplicationContext;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:02 PM)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        final ApplicationContext context = new DefaultApplicationContext(ContextDefinition.class);
        final ExecutionConfiguration configuration = context.getBean(ExecutionConfiguration.class);
    }

}
