package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.DocumentId;
import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.Indexed;


/**
 * Object for testing purposes. Should raise an exception during
 * indexation because non-primitive field is annotated as @Field.
 *
 * @author Jiří Holuša
 */
@Indexed
public class WrongAnnotatedEmbeddedEntity {

    @DocumentId
    @Field
    private int[] numbers = {1, 2};

}
