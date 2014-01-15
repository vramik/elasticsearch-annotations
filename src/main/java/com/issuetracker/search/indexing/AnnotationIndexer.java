package com.issuetracker.search.indexing;

import com.issuetracker.search.indexing.annotations.Indexed;
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
>>>>>>> 9b235ba... Basic functionality of @Field indexation
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
    private List<Object> visitedEntities = new ArrayList<Object>();

    @Override
    public void index(Object entity) {
        index(entity, "");
    }

    @Override
    public void index(Object entity, String prefix) {
        if(!isEntityAnnotated(entity)) {
            throw new IllegalArgumentException(); //TODO: change the text
        }

        if(prefix == null) {
            throw new IllegalArgumentException(); //TODO: change the text
        }

        if(visitedEntities.contains(entity)){
            return;
        }
        visitedEntities.add(entity);

        Dispatcher dispatcher = new AnnotationDispatcher(builder, this);

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
}
