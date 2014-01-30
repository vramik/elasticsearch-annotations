package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.builder.Builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * TODO: document this
 * @author Jiří Holuša
 */
public abstract class Processor {

    private String prefix;

    protected AnnotationIndexer indexer;
    protected Builder builder;
    protected Integer depth;
    protected Integer branchId;
    protected boolean processContainedIn;

    public abstract void process(Field field, Annotation annotation, Object entity);

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
