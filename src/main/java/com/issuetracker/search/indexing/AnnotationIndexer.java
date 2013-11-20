package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.annotations.Indexed;
import com.issuetracker.search.indexing.annotations.IndexedEntity;
import com.issuetracker.search.indexing.api.Indexer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */
public class AnnotationIndexer implements Indexer {

    @Override
    public void index(Object entity) {
        for(Field field: entity.getClass().getDeclaredFields()) {

            for(Annotation annotation: field.getDeclaredAnnotations()) {
                proceedAnnotation(field, annotation.annotationType());
            }

        }

    }

    private void proceedAnnotation(Field field, Class<? extends Indexed> annotation) {
        System.out.println("Indexed");
    }

    private void proceedAnnotation(Field field, Annotation annotation) {
        System.out.println("Annotation");
    }

}
