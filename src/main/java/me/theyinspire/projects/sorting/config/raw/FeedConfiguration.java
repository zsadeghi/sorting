package me.theyinspire.projects.sorting.config.raw;

/**
 * @author Zohreh Sadeghi (zsadeghi@uw.edu)
 * @since 1.0 (11/1/16, 10:27 PM)
 */
public class FeedConfiguration {

    private String type;
    private String path = "";
    private Integer column = 0;
    private Integer bound = 1;
    private String columnType = "string";
    private Integer size = 0;
    private Boolean ignoreFirst = true;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getBound() {
        return bound;
    }

    public void setBound(Integer bound) {
        this.bound = bound;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Boolean getIgnoreFirst() {
        return ignoreFirst;
    }

    public void setIgnoreFirst(Boolean ignoreFirst) {
        this.ignoreFirst = ignoreFirst;
    }

}
