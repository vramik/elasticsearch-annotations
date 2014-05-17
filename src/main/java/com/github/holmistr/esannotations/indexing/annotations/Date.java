package com.github.holmistr.esannotations.indexing.annotations;

import java.lang.annotation.*;

/**
 * TODO: document this
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Date {

    String format() default "YYYY-MM-dd";

}
