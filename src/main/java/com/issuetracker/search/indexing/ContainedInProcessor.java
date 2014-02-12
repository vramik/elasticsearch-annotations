package com.issuetracker.search.indexing;

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
