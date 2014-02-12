package com.issuetracker.search.indexing.annotations;

import java.lang.annotation.*;

/**
 * Annotation used to mark relationships as bidirectional, so the indexing
 * mechanism could update also index of the related entity
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ContainedIn {
}
