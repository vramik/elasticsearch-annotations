package com.issuetracker.search.indexing.processors;

import com.issuetracker.search.indexing.processors.api.Processor;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jirka
 * Date: 3.11.13
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
public class FieldAnnotationProcessor extends Processor {

    private Map<String, String> builder;

    public FieldAnnotationProcessor(Map<String, String> builder) {
        this.builder = builder;
    }

    @Override
    public void process(Field field, Annotation annotation, Object entity) {
        if(builder == null) {
            //TODO: customize this exception text
            throw new IllegalStateException();
        }

        Object value = null;
        field.setAccessible(true);
        try {
            value = field.get(entity);
        } catch (IllegalAccessException e) {
            //TODO: edit
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if(value == null){
            return;
        }

        builder.put(getPrefix() + field.getName(), value.toString());
    }
}
