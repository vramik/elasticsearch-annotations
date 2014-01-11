package com.issuetracker.test.search.indexer;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.Indexed;

/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */
@Indexed
public class Tester {

    @Field
    private String name;
    @Field
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
