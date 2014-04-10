package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.builder.Builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Represents processor that should handle current field. Used for polymorfism.
 *
 * @author Jiří Holuša
 */
public abstract class Processor {

    private String prefix;

    protected AnnotationIndexer indexer;
    protected Builder builder;
    protected Integer depth;
    protected Integer branchId;
    protected boolean processContainedIn;

    /**
     * Actually handles field processing.
     *
     * @param field field to be handled
     * @param annotation present annotation
     * @param entity owning object of the field
     */
    public abstract void process(Field field, Annotation annotation, Object entity);

    /**
     * Sets prefix that should be added to all fields.
     *
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
