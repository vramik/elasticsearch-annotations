package com.github.holmistr.esannotations.indexing.annotations;

import java.lang.annotation.*;

/**
 * Anotation to set analysis for field.
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Analyzer {

    /**
     * So far means nothing, only for storing into mapping builder.
     *
     * @return
     */
    String name() default "default";

    /**
     * Name of Elasticsearch build-in tokenizer to use.
     *
     * @return
     */
    String tokenizer() default "";

    /**
     * Comma-separated list of build in token filters to use.
     *
     * @return
     */
    String tokenFilters() default "";

}
