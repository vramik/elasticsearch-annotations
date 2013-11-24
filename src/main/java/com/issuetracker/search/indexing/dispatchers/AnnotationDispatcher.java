package com.issuetracker.search.indexing.dispatchers;

import com.issuetracker.search.indexing.annotations.Indexed;
import com.issuetracker.search.indexing.dispatchers.api.Dispatcher;
import com.issuetracker.search.indexing.processors.FieldAnnotationProcessor;
import com.issuetracker.search.indexing.processors.api.Processor;
import org.elasticsearch.common.xcontent.XContentBuilder;


import java.lang.annotation.Annotation;

/**
 * @author Jiri Holusa <jholusa@redhat.com>
 */
public class AnnotationDispatcher implements Dispatcher {

    private XContentBuilder builder;

    public AnnotationDispatcher(XContentBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Processor dispatch(Annotation annotation) {
        if(annotation instanceof Indexed) {
            return new FieldAnnotationProcessor(builder);
        }


        return null;
    }
}
