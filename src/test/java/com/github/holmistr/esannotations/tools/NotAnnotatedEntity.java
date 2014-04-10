package com.github.holmistr.esannotations.tools;

import com.github.holmistr.esannotations.indexing.annotations.Field;
import com.github.holmistr.esannotations.indexing.annotations.IndexEmbedded;

/**
 * Entity for testing purposes. Should raise an exception during indexation
 * because it's not annotated with @Indexed.
 *
 * @author Jiří Holuša
 */
public class NotAnnotatedEntity {

    @Field
    private String name;

    @IndexEmbedded
    private Address address;

}
