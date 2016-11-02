package me.theyinspire.projects.sorting.feed.impl;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:12 PM)
 */
public final class CsvColumnType<E extends Comparable<E>> {

    private final Class<E> type;
    private final ValueReader<E> reader;

    private CsvColumnType(Class<E> type, ValueReader<E> reader) {
        this.type = type;
        this.reader = reader;
    }

    public Class<E> getType() {
        return type;
    }

    public E getValue(String text) {
        return reader.read(text);
    }

    @FunctionalInterface
    private interface ValueReader<E> {

        E read(String text);

    }

    public static final CsvColumnType<String> STRING = new CsvColumnType<>(String.class, x -> x);
    public static final CsvColumnType<Integer> INTEGER = new CsvColumnType<>(Integer.class, Integer::parseInt);
    public static final CsvColumnType<Long> LONG = new CsvColumnType<>(Long.class, Long::parseLong);

}
