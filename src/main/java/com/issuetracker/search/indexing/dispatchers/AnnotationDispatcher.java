package com.issuetracker.search.indexing.dispatchers;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.annotations.Indexed;
import com.issuetracker.search.indexing.api.Indexer;
import com.issuetracker.search.indexing.dispatchers.api.Dispatcher;
import com.issuetracker.search.indexing.processors.FieldAnnotationProcessor;
import com.issuetracker.search.indexing.processors.IndexEmbeddedAnnotationProcessor;
import com.issuetracker.search.indexing.processors.api.Processor;
import org.elasticsearch.common.xcontent.XContentBuilder;


import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * TODO: document this
 * @author Jiří Holuša
 */
public class AnnotationDispatcher implements Dispatcher {

    private Map<String, String> builder;
    private Indexer indexer;

    public AnnotationDispatcher(Map<String, String> builder, Indexer indexer) {
        this.builder = builder;
        this.indexer = indexer;
    }

    @Override
    public Processor dispatch(Annotation annotation) {
        if(annotation instanceof Field) {
            return new FieldAnnotationProcessor(builder);
        }

        if(annotation instanceof IndexEmbedded) {
            return new IndexEmbeddedAnnotationProcessor(builder, indexer);
        }

        return null;
    }
}
