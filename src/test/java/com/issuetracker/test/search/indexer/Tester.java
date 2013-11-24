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
    private int id;
}
