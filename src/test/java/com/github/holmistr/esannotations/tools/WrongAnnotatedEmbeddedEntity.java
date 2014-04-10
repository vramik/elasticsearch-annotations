package com.github.holmistr.esannotations.tools;

import com.github.holmistr.esannotations.indexing.annotations.DocumentId;
import com.github.holmistr.esannotations.indexing.annotations.Field;
import com.github.holmistr.esannotations.indexing.annotations.Indexed;


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
