package com.issuetracker.search.indexing.dispatchers.api;

import com.issuetracker.search.indexing.processors.api.Processor;

import java.lang.annotation.Annotation;

/**
 * TODO: document this
 * @author Jiří Holuša
 */
public interface Dispatcher {

    public Processor dispatch(Annotation annotation);
}
