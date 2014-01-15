package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;

/**
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
public class NotAnnotatedEntity {

    @Field
    private String name;

    @IndexEmbedded
    private Address address;

}
