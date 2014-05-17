package com.github.holmistr.esannotations.indexing.annotations;

import java.lang.annotation.*;

/**
 * Annotation to mark simple primitive attributes.
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

    /**
     * Change the name of the field in the index. If not provided, name with be
     * selected as attribute name.
     *
     * @return
     */
    String name() default "";

    /**
     * //TODO: document this
     * @return
     */
    Analyze analyze() default Analyze.YES;

}
