package com.github.holmistr.esannotations.indexing.annotations;

import java.lang.annotation.*;

/**
 * Marks date attribute and allows to specify its format.
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Date {

    /**
     * Specify date format according to Elasticsearch date format.
     *
     * @return
     */
    String format() default "YYYY-MM-dd";

}
