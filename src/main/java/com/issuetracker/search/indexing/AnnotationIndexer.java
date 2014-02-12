package com.issuetracker.search.indexing;

<<<<<<< HEAD
import com.issuetracker.search.indexing.annotations.DocumentId;
import com.issuetracker.search.indexing.annotations.Indexed;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import com.issuetracker.search.indexing.api.Indexer;
import com.issuetracker.search.indexing.dispatchers.AnnotationDispatcher;
import com.issuetracker.search.indexing.dispatchers.api.Dispatcher;
import com.issuetracker.search.indexing.processors.api.Processor;
<<<<<<< HEAD
import static org.elasticsearch.common.xcontent.XContentFactory.*;

<<<<<<< HEAD
=======
import org.elasticsearch.common.xcontent.XContentBuilder;
>>>>>>> 36dacd8... Basics of indexer

=======
>>>>>>> 48a6516... @IndexEmbedded(depth) functionality introduced + structure reorganized

=======
>>>>>>> 9b235ba... Basic functionality of @Field indexation
=======
=======
=======
>>>>>>> 6f8fb9a... JavaDoc introduced + imports clean up
import com.issuetracker.search.indexing.builder.Builder;
import com.issuetracker.search.indexing.builder.MapAppendBuilder;
>>>>>>> 682b63b... @IndexEmbedded functionality for collections, arrays and maps introduced
import com.issuetracker.search.indexing.commons.BranchDuplicationDetectionTree;
import com.issuetracker.search.indexing.commons.Tree;

>>>>>>> b0a2db5... Cyclic indexation resolved
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
            throw new IllegalArgumentException(); //TODO: change the text
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
