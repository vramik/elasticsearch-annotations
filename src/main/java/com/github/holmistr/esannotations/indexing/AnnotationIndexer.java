package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.builder.Builder;
import com.github.holmistr.esannotations.indexing.builder.MapAppendBuilder;
import com.github.holmistr.esannotations.commons.BranchDuplicationDetectionTree;
import com.github.holmistr.esannotations.commons.Tree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


/**
 * Implementation of Indexer. Core class. Provides building of JSON-like
 * index according to annotated entities.
 *
 * @author Jiří Holuša
 */
public class AnnotationIndexer implements Indexer {

    private Builder builder = new MapAppendBuilder();
    private Tree<Class<?>> visitedEntities = new BranchDuplicationDetectionTree<Class<?>>();
    private Set<Object> markedModified = new HashSet<Object>();
    private Object indexedEntity;

    @Override
    public void index(Object entity) {
        index(entity, "");
    }

    @Override
    public void index(Object entity, String prefix) {
        visitedEntities.add(entity.getClass(), null, null);
        indexedEntity = entity;
        index(entity, prefix, null, null, true);
    }

    void index(Object entity, String prefix, Integer depth, Integer branchId, boolean processContainedIn) {
        if(depth != null && depth == 0) {
            return;
        }

        if(prefix == null) {
            throw new IllegalArgumentException("Prefix cannot be null.");
        }

        AnnotationDispatcher dispatcher = new AnnotationDispatcher(builder, this, depth, branchId);

        for(Field field: entity.getClass().getDeclaredFields()) {
            for(Annotation annotation: field.getDeclaredAnnotations()) {
                Processor processor = dispatcher.dispatch(field, annotation, entity, processContainedIn);

                if(processor != null) {
                    processor.setPrefix(prefix);
                    processor.process(field, annotation, entity);
                }
            }
        }
    }

    @Override
    public Map<String, String> getIndexOfSingleEntityAsMap() {
        return Collections.unmodifiableMap(builder.getIndexAsMap());
    }

    @Override
    public Map<Object, Map<String, String>> getCompleteIndexChanges() {
        Map<Object, Map<String, String>> returnValue = new HashMap<Object, Map<String, String>>();
        for(Object object: markedModified) {
            AnnotationIndexer indexer = new AnnotationIndexer();
            indexer.index(object, "", null, null, false);

            returnValue.put(object, indexer.getIndexOfSingleEntityAsMap());
        }

        returnValue.put(indexedEntity, builder.getIndexAsMap());

        return Collections.unmodifiableMap(returnValue);
    }

    Tree<Class<?>> getVisitedEntities() {
        return visitedEntities;
    }

    Set<Object> getMarkedModified() {
        return markedModified;
    }
}
