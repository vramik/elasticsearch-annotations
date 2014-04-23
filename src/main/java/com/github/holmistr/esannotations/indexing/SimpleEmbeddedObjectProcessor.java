package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.annotations.IndexEmbedded;
import com.github.holmistr.esannotations.indexing.builder.Builder;
import com.github.holmistr.esannotations.commons.CyclicIndexationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Implementation of Processor. Handles simple non-primitive objects (for instance custom classes,
 * not collections, etc.)
 *
 * @author Jiří Holuša
 */
public class SimpleEmbeddedObjectProcessor extends Processor {

    public SimpleEmbeddedObjectProcessor(Builder builder, AnnotationIndexer indexer,
                                         Integer depth, Integer branchId, boolean processContainedIn) {
        this.builder = builder;
        this.indexer = indexer;
        this.depth = depth;
        this.branchId = branchId;
        this.processContainedIn = processContainedIn;
    }

    @Override
    public void process(Field field, Annotation annotation, Object entity) {
        if(builder == null) {
            //TODO: customize this exception text
            throw new IllegalStateException();
        }

        if(indexer == null) {
            // TODO: customize this exception text
            throw new IllegalStateException();
        }

        Object embeddedObject = null;
        field.setAccessible(true);
        try {
            embeddedObject = field.get(entity);
        } catch (IllegalAccessException e) {
            //TODO: edit
        }

        if(embeddedObject == null) {
            return;
        }

        IndexEmbedded typedAnnotation = (IndexEmbedded) annotation;
        if(depth == null) {
            depth = typedAnnotation.depth();
        }
        else {
            if(depth != -1) {
                depth--;
            }
        }

        if(depth != null && depth == -1) {
            try {
                branchId = indexer.getVisitedEntities().add(embeddedObject.getClass(), entity.getClass(), branchId);
            } catch(IllegalStateException ex) {
                throw new CyclicIndexationException("Class " + embeddedObject.getClass().getName() +
                        " has already been processed.", ex);
            }
        }

        String fieldName = typedAnnotation.name().isEmpty() ? field.getName() : typedAnnotation.name();
        indexer.index(embeddedObject, getPrefix() + fieldName + ".", depth, branchId, processContainedIn);
    }
}
