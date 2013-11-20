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
public interface Processor {

    public void process(Field field, Annotation annotation, Object entity);
}
