package com.github.holmistr.esannotations.indexing;

import com.github.holmistr.esannotations.indexing.annotations.Analyzer;
import com.github.holmistr.esannotations.indexing.annotations.Date;
import com.github.holmistr.esannotations.indexing.builder.Builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

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
            throw new IllegalStateException("Builder cannot be null.");
        }

        Object value = null;
        field.setAccessible(true);
        try {
            value = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to get value of the field.", e);
        }

        if(value == null){
            return;
        }

        com.github.holmistr.esannotations.indexing.annotations.Field typedAnnotation = (com.github.holmistr.esannotations.indexing.annotations.Field) annotation;
        String fieldName = typedAnnotation.name().isEmpty() ? field.getName() : typedAnnotation.name();
        String fieldValue = value.toString();

        Analyzer analyzerAnnotation = getAnalyzerAnnotation(field);
        if(analyzerAnnotation != null) {
            String analyzerName = analyzerAnnotation.name();
            String tokenizer = analyzerAnnotation.tokenizer();
            String[] tokenFilters = analyzerAnnotation.tokenFilters().split(",");

            if(!analyzerName.isEmpty()) {
                builder.putAnalyzer(analyzerName, "type", "custom");
                builder.putMapping(getPrefix() + fieldName, "type", "string");
                builder.putMapping(getPrefix() + fieldName, "analyzer", analyzerName);

                if(!tokenizer.isEmpty()) {
                    builder.putAnalyzer(analyzerName, "tokenizer", tokenizer);
                }

                if(tokenFilters != null && tokenFilters.length != 0) {
                    builder.putAnalyzer(analyzerName, "filter", tokenFilters);
                }
            }
        }

        Date dateAnnotation = getDateAnnotation(field);
        if(dateAnnotation != null) {
            builder.putMapping(getPrefix() + fieldName, "type", "date");
            builder.putMapping(getPrefix() + fieldName, "format", dateAnnotation.format());

            java.util.Date dateValue = (java.util.Date) value;
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateAnnotation.format());
            fieldValue = dateFormat.format(dateValue);
        }

        builder.put(getPrefix() + fieldName, fieldValue);
    }

    private Analyzer getAnalyzerAnnotation(Field field) {
        for(Annotation annotation: field.getDeclaredAnnotations()) {
            if(annotation instanceof Analyzer) {
                return (Analyzer) annotation;
            }
        }

        return null;
    }

    private Date getDateAnnotation(Field field) {
        for(Annotation annotation: field.getDeclaredAnnotations()) {
            if(annotation instanceof Date) {
                return (Date) annotation;
            }
        }

        return null;
    }
}
