package com.github.holmistr.esannotations.tools;

import com.github.holmistr.esannotations.indexing.annotations.DocumentId;
import com.github.holmistr.esannotations.indexing.annotations.IndexEmbedded;
import com.github.holmistr.esannotations.indexing.annotations.Indexed;

/**
 * Object for testing purposes. Should raise an exception during
 * indexation because primitive field is annotated with @IndexEmbedded
 *
 * @author Jiří Holuša
 */
@Indexed
public class WrongAnnotatedPrimitiveEntity {

    @DocumentId
    @IndexEmbedded
    private String name = "Not Lucky";

}
