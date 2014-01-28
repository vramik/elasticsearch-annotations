package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.annotations.Indexed;
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

    private Map<String, String> builder = new HashMap<String, String>();
    private Tree<Class<?>> visitedEntities = new BranchDuplicationDetectionTree<Class<?>>();

    @Override
    public void index(Object entity) {
        index(entity, "");
    }

    @Override
    public void index(Object entity, String prefix) {
        visitedEntities.add(entity.getClass(), null, null);
        index(entity, prefix, null, null);
    }

    void index(Object entity, String prefix, Integer depth, Integer branchId) {
        if(!isEntityAnnotated(entity)) {
            throw new IllegalArgumentException(); //TODO: change the text
        }

        if(depth != null && depth == 0) {
            return;
        }

        if(prefix == null) {
            throw new IllegalArgumentException(); //TODO: change the text
        }

        /*if(depth != null && depth == -1) {
            visitedEntities.add(entity.getClass());
        } */

        AnnotationDispatcher dispatcher = new AnnotationDispatcher(builder, this, depth, branchId);

        for(Field field: entity.getClass().getDeclaredFields()) {
            for(Annotation annotation: field.getDeclaredAnnotations()) {
                Processor processor = dispatcher.dispatch(annotation);
                processor.setPrefix(prefix);

                if(processor != null) {
                    processor.process(field, annotation, entity);
                }
            }
        }
    }

    @Override
    public Map<String, String> getIndexAsMap() {
        return Collections.unmodifiableMap(builder);
    }

    private boolean isEntityAnnotated(Object entity) {
        Annotation[] annotations = entity.getClass().getDeclaredAnnotations();
        for(Annotation annotation: annotations) {
            if(annotation instanceof Indexed) {
                return true;
            }
        }

        return false;
    }

    Tree<Class<?>> getVisitedEntities() {
        return visitedEntities;
    }
}
