package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.annotations.DocumentId;
import com.issuetracker.search.indexing.annotations.Indexed;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * High-level manager. Used for automated index creation according to annotated
 * entities and sending the index into Elasticsearch.
 *
 * @author Jiří Holuša
 */
public class AnnotationIndexManager {

    /* Elasticsearch client that is used for communication with server */
    private Client client;

    /**
     *
     * @param client Elasticsearch client that is used for communication with server
     */
    public AnnotationIndexManager(Client client) {
        this.client = client;
    }

    /**
     * Alias for index(entity, false), e.g. indexation without refreshing
     *
     * @param entity
     */
    public void index(Object entity) {
        index(entity, false);
    }

    /**
     * Creates index of the entity and also recreates all indexes that
     * has been affected by this indexation (for example because of associations).
     * Than sends the index into Elasticsearch via provided client
     * and stores the index according to setting in @Indexed annotation
     * and @DocumentId annotation.
     *
     * @param entity entity to be indexed
     * @param refresh if index changes should be immediately refreshed
     */
    public void index(Object entity, boolean refresh) {
        isEntityProperlyAnnotated(entity);

        AnnotationIndexer indexer = new AnnotationIndexer();
        indexer.index(entity);
        Map<Object, Map<String, String>> indexChanges = indexer.getCompleteIndexChanges();

        for(Object key: indexChanges.keySet()) {
            String index = getEntityIndex(key);
            String type = getEntityType(key);
            String documentId = getDocumentId(key);

            //convert is necessary because Client.setSource takes Map<String, Object>, not Map<String, String>
            Map<String, Object> convertedMap = new HashMap<String, Object>(indexChanges.get(key));

            IndexResponse response = client.prepareIndex(index, type, documentId)
                    .setSource(convertedMap)
                    .setRefresh(refresh)
                    .execute()
                    .actionGet();
        }
    }

    private String getDocumentId(Object entity) {
        for(Field field: entity.getClass().getDeclaredFields()) {
            for(Annotation annotation: field.getDeclaredAnnotations()) {
                if(annotation instanceof DocumentId) {
                    Object documentId = null;
                    field.setAccessible(true);
                    try {
                        documentId = field.get(entity);
                    } catch (IllegalAccessException e) {
                        //TODO: edit
                    }

                    if(documentId == null) {
                        throw new IllegalArgumentException("Entity's document ID cannot be null.");
                    }

                    return documentId.toString();
                }
            }
        }

        throw new IllegalArgumentException("Entity's document ID not found.");
    }

    private String getEntityIndex(Object entity) {
        Indexed indexedAnnotation = entity.getClass().getAnnotation(Indexed.class);
        String index = indexedAnnotation.index();

        return index;
    }

    private String getEntityType(Object entity) {
        Indexed indexedAnnotation = entity.getClass().getAnnotation(Indexed.class);
        String type = indexedAnnotation.type();

        if(type.length() == 0) {
            type = entity.getClass().getSimpleName();
        }

        return type;
    }

    private void isEntityProperlyAnnotated(Object entity) {
        if(!entity.getClass().isAnnotationPresent(Indexed.class)) {
            throw new IllegalArgumentException("Entity " + entity.getClass().getName() + " must be annotated @Indexed.");
        }

        boolean documentIdFound = false;
        for(Field field: entity.getClass().getDeclaredFields()) {
            for(Annotation annotation: field.getDeclaredAnnotations()) {
                if(annotation instanceof DocumentId) {
                    if(documentIdFound) {
                        throw new IllegalArgumentException("Entity " + entity.getClass().getName() + " cannot have more that one @DocumentId's.");
                    }

                    Object documentId = null;
                    field.setAccessible(true);
                    try {
                        documentId = field.get(entity);
                    } catch (IllegalAccessException e) {
                        //TODO: edit
                    }

                    if(documentId == null) {
                        throw new IllegalArgumentException("Entity's document ID cannot be null.");
                    }

                    documentIdFound = true;
                }
            }
        }

        if(!documentIdFound) {
            throw new IllegalArgumentException("Entity " + entity.getClass().getName() + " doesn't have @DocumentId field.");
        }
    }
}
