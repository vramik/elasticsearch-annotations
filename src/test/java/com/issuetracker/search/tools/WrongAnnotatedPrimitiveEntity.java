package com.issuetracker.search.tools;

import com.issuetracker.search.indexing.annotations.DocumentId;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.annotations.Indexed;

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
