package com.issuetracker.search.indexing.dispatchers;

import com.issuetracker.search.indexing.annotations.Indexed;
import com.issuetracker.search.indexing.dispatchers.api.Dispatcher;
import com.issuetracker.search.indexing.processors.IndexedAnnotationProcessor;
import com.issuetracker.search.indexing.processors.api.Processor;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.lang.annotation.Annotation;

/**
 * @author Jiri Holusa <jholusa@redhat.com>
 */
public class AnnotationDispatcher implements Dispatcher {

    private XContentBuilder builder;

    public AnnotationDispatcher(XContentBuilder builder) {
        // TODO: handle null
        this.builder = builder;
    }

    @Override
    public Processor dispatch(Annotation annotation) {
        if(annotation instanceof Indexed) {
            return new IndexedAnnotationProcessor(builder);
        }

        // TODO: change exception text
        throw new IllegalArgumentException();
    }
}
