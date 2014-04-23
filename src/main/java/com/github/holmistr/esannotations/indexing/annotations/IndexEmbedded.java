package com.github.holmistr.esannotations.indexing.annotations;

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
     * Change the name of the field in the index. If not provided, name with be
     * selected as attribute name.
     *
     * @return
     */
    String name() default "";

    /**
     * Specify depth of the indexation. If not provided, the indexation
     * will stop if the same class has already been processed in the currect
     * branch of processing graph.
     *
     * @return
     */
    int depth() default -1;
}
