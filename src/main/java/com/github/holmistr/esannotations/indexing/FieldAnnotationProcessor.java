package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.builder.Builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Implementation of Processor. Handles fields annotated with @Field.
 * @author Jiří Holuša
 */
public class FieldAnnotationProcessor extends Processor {

    private Builder builder;

    public FieldAnnotationProcessor(Builder builder) {
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

        com.github.holmistr.esannotations.indexing.annotations.Field typedAnnotation = (com.github.holmistr.esannotations.indexing.annotations.Field) annotation;
        String fieldName = typedAnnotation.name().isEmpty() ? field.getName() : typedAnnotation.name();
        builder.put(getPrefix() + fieldName, value.toString());
    }
}
