package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.annotations.IndexEmbedded;
import com.issuetracker.search.indexing.commons.CyclicIndexationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * //TODO: document this
 * @author Jiří Holuša
 */
public class SimpleEmbeddedObjectProcessor extends Processor {

    private AnnotationIndexer indexer;
    private Map<String, String> builder;
    private Integer depth;
    private Integer branchId;

    public SimpleEmbeddedObjectProcessor(Map<String, String> builder, AnnotationIndexer indexer, Integer depth, Integer branchId) {
        this.builder = builder;
        this.indexer = indexer;
        this.depth = depth;
        this.branchId = branchId;
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

        if(depth == null) {
            IndexEmbedded typedAnnotation = (IndexEmbedded) annotation;
            depth = typedAnnotation.depth();
        }
        else {
            depth--;
        }

        if(depth != null && depth == -1) {
            try {
                branchId = indexer.getVisitedEntities().add(embeddedObject.getClass(), entity.getClass(), branchId);
            } catch(IllegalStateException ex) {
                throw new CyclicIndexationException("Class " + embeddedObject.getClass().getName() +
                        " has already been processed.", ex);
            }
        }

        indexer.index(embeddedObject, getPrefix() + field.getName() + ".", depth, branchId);
    }
}
