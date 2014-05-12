package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.builder.Builder;
import com.github.holmistr.esannotations.indexing.builder.MapAppendBuilder;
import com.github.holmistr.esannotations.commons.BranchDuplicationDetectionTree;
import com.github.holmistr.esannotations.commons.Tree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


/**
 * Core class. Provides building of JSON-like
 * index according to annotated entities.
 *
 * @author Jiří Holuša
 */
public class AnnotationIndexer {

    private Builder builder = new MapAppendBuilder();
    private Tree<Class<?>> visitedEntities = new BranchDuplicationDetectionTree<Class<?>>();
    private Set<Object> markedModified = new HashSet<Object>();
    private Object indexedEntity;

    /**
     * Same as index(entity, "")
     *
     * @param entity
     */
    public void index(Object entity) {
        index(entity, "");
    }

    /**
     * Creates index of the entity with provided prefix that is added to
     * every key of the index.
     *
     * @param entity entity to be indexed
     * @param prefix prefix to be added
     */
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

    /**
     * Return the index of the entity that was indexed as Map.
     *
     * @return
     */
    public Map<String, String> getIndexOfSingleEntityAsMap() {
        return Collections.unmodifiableMap(builder.getIndexAsMap());
    }

    /**
     * Return the indexes of all entities that was affected by indexation
     * of entity and therefore their index has to be rebuilt, for example because of
     * existence of relationship or being embedded.
     *
     * @return Map of all entities and their new indexes. Keys are the entities, values
     * are indexes of those entities.
     */
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
