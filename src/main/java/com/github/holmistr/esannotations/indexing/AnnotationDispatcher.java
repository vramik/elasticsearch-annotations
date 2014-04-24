package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.annotations.ContainedIn;
import com.github.holmistr.esannotations.indexing.annotations.Field;
import com.github.holmistr.esannotations.indexing.annotations.IndexEmbedded;
import com.github.holmistr.esannotations.indexing.builder.Builder;
import com.github.holmistr.esannotations.commons.TypeChecker;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/**
 * Delegates the processing to processor based on the annotations
 * present on field and their parameters/types.
 *
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

    /**
     * Chooses correct processor for supplied field.
     *
     * @param field field to be processed
     * @param annotation present annotation
     * @param entity object that contains the field
     * @param processContainedIn flag if @ContainedIn annotation should be taken into account
     * @return
     */
    public Processor dispatch(java.lang.reflect.Field field, Annotation annotation, Object entity, boolean processContainedIn) {
        Object embeddedObject = null;
        field.setAccessible(true);
        try {
            embeddedObject = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to get value of the field.", e);
        }

        if(embeddedObject == null) {
            return null;
        }

        if(annotation instanceof Field) {
            if(!TypeChecker.isPrimitiveOrString(embeddedObject.getClass())) {
                throw new IllegalArgumentException("Field annotation can be applied only to primitives or string.");
            }

            return new FieldAnnotationProcessor(builder);
        }

        if(annotation instanceof IndexEmbedded) {
            if(TypeChecker.isPrimitiveOrString(embeddedObject.getClass())) {
                throw new IllegalArgumentException("IndexEmbedded annotation cannot be applied to primitives.");
            }

            if(embeddedObject instanceof Collection) {
                return new EmbeddedCollectionProcessor(builder, indexer, depth, branchId, processContainedIn);
            }

            if(embeddedObject instanceof Map) {
                return new EmbeddedMapProcessor(builder, indexer, depth, branchId, processContainedIn);
            }

            if(embeddedObject.getClass().isArray()) {
                return new EmbeddedArrayProcessor(builder, indexer, depth, branchId, processContainedIn);
            }

            return new SimpleEmbeddedObjectProcessor(builder, indexer, depth, branchId, processContainedIn);
        }

        if(processContainedIn && annotation instanceof ContainedIn) {
            if(TypeChecker.isPrimitiveOrString(embeddedObject.getClass())) {
                throw new IllegalArgumentException("ContainedIn annotation cannot be applied to primitives.");
            }

            return new ContainedInProcessor(indexer);
        }

        return null;
    }
}
