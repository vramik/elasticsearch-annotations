package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.annotations.IndexEmbedded;
import com.github.holmistr.esannotations.indexing.builder.Builder;
import com.github.holmistr.esannotations.commons.CyclicIndexationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Implementation of Processor. Handles Array fields annotated with @IndexEmbedded.
 *
 * @author Jiří Holuša
 */
public class EmbeddedArrayProcessor extends Processor {

    public EmbeddedArrayProcessor(Builder builder, AnnotationIndexer indexer,
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

        if(!embeddedObject.getClass().isArray()) {
            throw new IllegalStateException(); //TODO: change the text
        }

        Object[] embeddedArray = (Object[]) embeddedObject;

        IndexEmbedded typedAnnotation = (IndexEmbedded) annotation;
        if(depth == null) {
            depth = typedAnnotation.depth();
        }
        else {
            if(depth != -1) {
                depth--;
            }
        }

        for(Object object: embeddedArray) {
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
