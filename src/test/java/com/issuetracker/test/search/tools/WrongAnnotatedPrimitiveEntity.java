package com.issuetracker.test.search.tools;

import com.issuetracker.search.indexing.annotations.DocumentId;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.annotations.Indexed;

/**
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
@Indexed
public class WrongAnnotatedPrimitiveEntity {

    @DocumentId
    @IndexEmbedded
    private String name = "Not Lucky";

}
