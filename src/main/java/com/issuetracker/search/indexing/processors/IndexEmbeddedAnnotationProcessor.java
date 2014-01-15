package com.issuetracker.search.indexing.processors;

import com.issuetracker.search.indexing.api.Indexer;
import com.issuetracker.search.indexing.processors.api.Processor;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * //TODO: document this
 * @author Jiří Holuša
 */
public class IndexEmbeddedAnnotationProcessor extends Processor {

    private Indexer indexer;
    private Map<String, String> builder;

    public IndexEmbeddedAnnotationProcessor(Map<String, String> builder, Indexer indexer) {
        this.builder = builder;
        this.indexer = indexer;
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

        indexer.index(embeddedObject, getPrefix() + field.getName() + ".");
    }
}
