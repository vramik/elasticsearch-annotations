package com.github.holmistr.esannotations.indexing.annotations;

import java.lang.annotation.*;

/**
 * Annotations marks entity as usable to indexation mechanism.
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Indexed {

    /**
     * Allow to identify the index name location.
     * If no value set, "default" index name will be provided.
     */
    String index() default "default";

    /**
     * Allow to identify the type name location.
     * If no value set, type name will be generated from class name.
     * @return
     */
    String type() default "";
}
