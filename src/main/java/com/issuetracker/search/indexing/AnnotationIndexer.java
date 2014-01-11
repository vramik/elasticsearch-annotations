package com.issuetracker.search.indexing;

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


/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */
public class AnnotationIndexer implements Indexer {

    private XContentBuilder builder;// = jsonBuilder();

    @Override
    public void index(Object entity) {
        indexEntity(entity);
    }

    private void indexEntity(Object entity) {
        // TODO: check if entity is annotated with @Indexed

        Dispatcher dispatcher = new AnnotationDispatcher(builder);

        for(Field field: entity.getClass().getDeclaredFields()) {
            for(Annotation annotation: field.getDeclaredAnnotations()) {
                Processor processor = dispatcher.dispatch(annotation);

                if(processor != null) {
                    processor.process(field, annotation, entity);
                }
            }
        }
    }

    public XContentBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(XContentBuilder builder) {
        this.builder = builder;
    }
}
