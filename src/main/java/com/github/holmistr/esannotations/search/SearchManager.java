package com.github.holmistr.esannotations.search;

import com.github.holmistr.esannotations.commons.TypeChecker;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * //TODO: document this
 *
 * @author Jiří Holuša
 */
public class SearchManager {

    private Client client;

    public SearchManager(Client client) {
        this.client = client;
    }

    public <T> List<T> search(SearchResponse searchResponse, Class<T> clazz) {
        List<T> result = new ArrayList<T>();

        for(SearchHit hit: searchResponse.getHits()) {
            T entity = mapMapToEntity(hit.getSource(), clazz);
            result.add(entity);
        }

        return result;
    }

    public <T> T get(GetResponse getResponse, Class<T> clazz) {
        T result = mapMapToEntity(getResponse.getSource(), clazz);

        return result;
    }

    public Client getClient() {
        return client;
    }

    private <T> T mapMapToEntity(Map<String, Object> source, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            //TODO: handle exception properly
        } catch (IllegalAccessException e) {
            //TODO: handle exception properly
        }

        for(Field field: clazz.getDeclaredFields()) {
            for(Annotation annotation: field.getDeclaredAnnotations()) {
                if(annotation instanceof com.github.holmistr.esannotations.indexing.annotations.Field) {
                    com.github.holmistr.esannotations.indexing.annotations.Field typedAnnotation = (com.github.holmistr.esannotations.indexing.annotations.Field)annotation;

                    if(source.containsKey(field.getName()) || source.containsKey(typedAnnotation.name())) {
                        String fieldName = source.containsKey(typedAnnotation.name()) ? typedAnnotation.name() : field.getName();
                        if(!TypeChecker.isPrimitiveOrString(field.getType())) {
                            throw new IllegalStateException(); //TODO: handle this exception properly
                        }

                        field.setAccessible(true);
                        try {
                            Object value =  TypeChecker.castObjectToPrimitive(source.get(fieldName).toString(), field.getType());
                            field.set(result, value);
                        } catch (IllegalAccessException e) {
                            //TODO: handle exception properly
                        }
                    }
                }
            }
        }

        return result;
    }
}
