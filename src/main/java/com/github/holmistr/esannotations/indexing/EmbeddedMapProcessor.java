package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.annotations.IndexEmbedded;
import com.github.holmistr.esannotations.indexing.builder.Builder;
import com.github.holmistr.esannotations.commons.CyclicIndexationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Implementation of Processor. Handles Map fields annotated with @IndexEmbedded.
 *
 * @author Jiří Holuša
 */
public class EmbeddedMapProcessor extends Processor {

    public EmbeddedMapProcessor(Builder builder, AnnotationIndexer indexer, Integer depth, Integer branchId, boolean processContainedIn) {
        this.builder = builder;
        this.indexer = indexer;
        this.depth = depth;
        this.branchId = branchId;
        this.processContainedIn = processContainedIn;
    }

    @Override
    public void process(Field field, Annotation annotation, Object entity) {
        if(builder == null) {
            throw new IllegalStateException("Builder cannot be null.");
        }

        if(indexer == null) {
            throw new IllegalStateException("Indexer cannot be null.");
        }

        Object embeddedObject = null;
        field.setAccessible(true);
        try {
            embeddedObject = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to get value of the field.", e);
        }

        if(embeddedObject == null) {
            return;
        }

        if(!(embeddedObject instanceof Map)) {
            throw new IllegalStateException("Field is not a map.");
        }

        Map<?, ?> embeddedMap = (Map<?, ?>) embeddedObject;

        IndexEmbedded typedAnnotation = (IndexEmbedded) annotation;
        if(depth == null) {
            depth = typedAnnotation.depth();
        }
        else {
            if(depth != -1) {
                depth--;
            }
        }

        for(Object object: embeddedMap.values()) {
            if(depth != null && depth == -1) {
                try {
                    branchId = indexer.getVisitedEntities().add(object.getClass(), entity.getClass(), branchId);
                } catch(IllegalStateException ex) {
                    throw new CyclicIndexationException("Class " + object.getClass().getName() +
                            " has already been processed.", ex);
                }
            }

            String fieldName = typedAnnotation.name().isEmpty() ? field.getName() : typedAnnotation.name();
            indexer.index(object, getPrefix() + fieldName + ".", depth, branchId, processContainedIn);
        }
    }
}
