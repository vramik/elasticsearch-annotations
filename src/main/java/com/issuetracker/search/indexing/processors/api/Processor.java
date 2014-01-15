package com.issuetracker.search.indexing.processors.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * TODO: document this
 * @author Jiří Holuša
 */
public abstract class Processor {

    private String prefix;

    public abstract void process(Field field, Annotation annotation, Object entity);

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
