package com.issuetracker.search.indexing.processors;

import com.issuetracker.search.indexing.processors.api.Processor;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Jirka
 * Date: 3.11.13
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
public class FieldAnnotationProcessor implements Processor {

    private XContentBuilder builder;

    public FieldAnnotationProcessor(XContentBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void process(Field field, Annotation annotation, Object entity) {
        if(builder == null) {
            //TODO: customize this exception text
            throw new IllegalStateException();
        }

        String value = null;
        field.setAccessible(true);
        try {
            value = field.get(entity).toString();
        } catch (IllegalAccessException e) {
            //TODO: edit
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            builder.field(field.getName(), value);
        } catch (IOException e) {
            //TODO: edit
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
