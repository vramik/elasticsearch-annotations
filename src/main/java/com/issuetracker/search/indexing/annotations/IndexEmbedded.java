package com.issuetracker.search.indexing.annotations;

import java.lang.annotation.*;

/**
 * Marks attribute as embedded, therefore index will be included into
 * "parent" entity index.
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IndexEmbedded {

    /**
     * Specify depth of the indexation. If not provided, the indexation
     * will stop if the same class has already been processed in the currect
     * branch of processing graph.
     *
     * @return
     */
    int depth() default -1;
}
