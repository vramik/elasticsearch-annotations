package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.annotations.Field;
import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.builder.Builder;
import com.issuetracker.search.indexing.commons.TypeChecker;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/**
 * TODO: document this
 * @author Jiří Holuša
 */
public class AnnotationDispatcher {

    private Builder builder;
    private AnnotationIndexer indexer;
    private Integer depth;
    private Integer branchId;

    public AnnotationDispatcher(Builder builder, AnnotationIndexer indexer, Integer depth, Integer branchId) {
        this.builder = builder;
        this.indexer = indexer;
        this.depth = depth;
        this.branchId = branchId;
    }

    public Processor dispatch(java.lang.reflect.Field field, Annotation annotation, Object entity) {
        Object embeddedObject = null;
        field.setAccessible(true);
        try {
            embeddedObject = field.get(entity);
        } catch (IllegalAccessException e) {
            //TODO: edit
        }

        if(embeddedObject == null) {
            return null;
        }

        if(annotation instanceof Field) {
            if(!TypeChecker.isPrimitiveOrString(embeddedObject.getClass())) {
                throw new IllegalArgumentException(); //TODO: change the text
            }

            return new FieldAnnotationProcessor(builder);
        }

        if(annotation instanceof IndexEmbedded) {
            if(TypeChecker.isPrimitiveOrString(embeddedObject.getClass())) {
                throw new IllegalArgumentException(); //TODO: change the text
            }

            if(embeddedObject instanceof Collection) {
                return new EmbeddedCollectionProcessor(builder, indexer, depth, branchId);
            }

            if(embeddedObject instanceof Map) {
                return new EmbeddedMapProcessor(builder, indexer, depth, branchId);
            }

            if(embeddedObject.getClass().isArray()) {
                return new EmbeddedArrayProcessor(builder, indexer, depth, branchId);
            }

            return new SimpleEmbeddedObjectProcessor(builder, indexer, depth, branchId);
        }

        return null;
    }
}
