package me.theyinspire.projects.sorting.stats;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/2/16, 1:37 PM)
 */
public interface AnalysisWriter {

    <X extends Number, Y extends Number> void write(Analysis<X, Y> analysis);

}
