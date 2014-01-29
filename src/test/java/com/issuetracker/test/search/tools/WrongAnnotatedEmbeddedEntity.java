package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.Indexed;


/**
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
@Indexed
public class WrongAnnotatedEmbeddedEntity {

    @Field
    private int[] numbers = {1, 2};

}
