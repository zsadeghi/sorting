package me.theyinspire.projects.sorting.feed.impl;

import me.theyinspire.projects.sorting.feed.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:11 PM)
 */
public class CsvDataFeed<E extends Comparable<E>> extends AbstractDataFeed<E> {

    private final CsvColumnType<E> type;
    private final int column;
    private final String path;
    private final boolean ignoreFirst;
    private CSVParser records;
    private Iterator<CSVRecord> iterator;

    public CsvDataFeed(CsvColumnType<E> type, int column, String path, boolean ignoreFirst) throws IOException {
        this.type = type;
        this.column = column;
        this.path = path;
        this.ignoreFirst = ignoreFirst;
    }

    @Override
    public Record<E> next() {
        final CSVRecord record = iterator.next();
        return new ImmutableRecord<E>(record.getRecordNumber(), type.getValue(record.get(column)));
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public void reset() {
        try {
            if (records != null && !records.isClosed()) {
                records.close();
            }
            final FileReader reader = new FileReader(path);
            records = CSVFormat.EXCEL.parse(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        iterator = records.iterator();
        if (ignoreFirst && iterator.hasNext()) {
            iterator.next();
        }
    }

    @Override
    public void close() throws IOException {
        records.close();
    }

}
