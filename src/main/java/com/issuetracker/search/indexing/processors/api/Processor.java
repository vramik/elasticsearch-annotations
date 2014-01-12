package com.issuetracker.search.indexing.processors.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Jirka
 * Date: 3.11.13
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
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
