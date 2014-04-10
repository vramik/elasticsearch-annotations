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
}
