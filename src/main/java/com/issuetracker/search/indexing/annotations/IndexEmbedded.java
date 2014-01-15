package com.issuetracker.search.indexing.annotations;

import java.lang.annotation.*;

/**
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IndexEmbedded {
}
