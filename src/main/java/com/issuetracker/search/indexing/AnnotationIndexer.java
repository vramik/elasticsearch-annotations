package com.issuetracker.search.indexing;

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
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
public class AnnotationIndexer implements Indexer {

    private Builder builder = new MapAppendBuilder();
    private Tree<Class<?>> visitedEntities = new BranchDuplicationDetectionTree<Class<?>>();

    @Override
    public void index(Object entity) {
        index(entity, "");
    }

    @Override
    public void index(Object entity, String prefix) {
        if(!entity.getClass().isAnnotationPresent(Indexed.class)) {
            throw new IllegalArgumentException(); //TODO: change the text
        }

        visitedEntities.add(entity.getClass(), null, null);
        index(entity, prefix, null, null);
    }

    void index(Object entity, String prefix, Integer depth, Integer branchId) {
        if(depth != null && depth == 0) {
            return;
        }

        if(prefix == null) {
            throw new IllegalArgumentException(); //TODO: change the text
        }

        AnnotationDispatcher dispatcher = new AnnotationDispatcher(builder, this, depth, branchId);

        for(Field field: entity.getClass().getDeclaredFields()) {
            for(Annotation annotation: field.getDeclaredAnnotations()) {
                Processor processor = dispatcher.dispatch(field, annotation, entity);

                if(processor != null) {
                    processor.setPrefix(prefix);
                    processor.process(field, annotation, entity);
                }
            }
        }
    }

    @Override
    public Map<String, String> getIndexAsMap() {
        return Collections.unmodifiableMap(builder.getIndexAsMap());
    }

    Tree<Class<?>> getVisitedEntities() {
        return visitedEntities;
    }
}
