package com.github.holmistr.esannotations.indexing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Implementation of Processor. Handles fields annotated with @ContainedIn.
 *
 * @author Jiří Holuša
 */
public class ContainedInProcessor extends Processor {

    public ContainedInProcessor(AnnotationIndexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public void process(Field field, Annotation annotation, Object entity) {
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

        if(embeddedObject instanceof Collection) {
            Collection<?> collection = (Collection) embeddedObject;
            for(Object object: collection) {
                indexer.getMarkedModified().add(object);
            }

            return;
        }

        if(embeddedObject.getClass().isArray()) {
            Object[] array = (Object[]) embeddedObject;
            for(Object object: array) {
                indexer.getMarkedModified().add(object);
            }

            return;
        }

        if(embeddedObject instanceof Map) {
            Map<?, ?> map = (Map<?,?>) embeddedObject;
            for(Object object: map.values()) {
                indexer.getMarkedModified().add(object);
            }

            return;
        }

        indexer.getMarkedModified().add(embeddedObject);
    }
}
