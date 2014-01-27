package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.dispatchers.api.Dispatcher;


import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * TODO: document this
 * @author Jiří Holuša
 */
public class AnnotationDispatcher implements Dispatcher {

    private Map<String, String> builder;
    private AnnotationIndexer indexer;
    private Integer depth;

    public AnnotationDispatcher(Map<String, String> builder, AnnotationIndexer indexer, Integer depth) {
        this.builder = builder;
        this.indexer = indexer;
        this.depth = depth;
    }

    @Override
    public Processor dispatch(Annotation annotation) {
        if(annotation instanceof Field) {
            return new FieldAnnotationProcessor(builder);
        }

        if(annotation instanceof IndexEmbedded) {
            return new SimpleEmbeddedObjectProcessor(builder, indexer, depth);
        }

        return null;
    }
}