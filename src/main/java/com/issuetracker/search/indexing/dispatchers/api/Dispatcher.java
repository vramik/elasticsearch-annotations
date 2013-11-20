package com.issuetracker.search.indexing.dispatchers.api;

import com.issuetracker.search.indexing.processors.api.Processor;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: Jirka
 * Date: 3.11.13
 * Time: 21:06
 * To change this template use File | Settings | File Templates.
 */
public interface Dispatcher {

    public Processor dispatch(Annotation annotation);
}
