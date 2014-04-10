package com.github.holmistr.esannotations.indexing.annotations;

import java.lang.annotation.*;

/**
 * Annotation marks attributed that is used as primary key of the
 * index.
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DocumentId {
}
