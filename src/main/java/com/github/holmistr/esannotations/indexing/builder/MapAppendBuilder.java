package com.github.holmistr.esannotations.indexing.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of Builder interface. The put creates new entry if the key
 * doesn't exist, otherwise it appends to the current value.
 *
 * @author Jiří Holuša
 */
public class MapAppendBuilder implements Builder {

    private static final String PROPERTIES_KEY = "properties";
    private static final String ANALYSIS_KEY = "analysis";
    private static final String ANALYZER_KEY = "analyzer";

    private Map<String, StringBuilder> builder = new HashMap<String, StringBuilder>();

    private Map<String, Map<String, Map<String, String>>> mappings = new HashMap<String, Map<String, Map<String, String>>>();
    private Map<String, Map<String, Map<String, Map<String, Object>>>> settings = new HashMap<String, Map<String, Map<String, Map<String, Object>>>>();

    @Override
    public void put(String key, String value) {
        if(!builder.containsKey(key)) {
            builder.put(key, new StringBuilder());
        }

        StringBuilder previousValue = builder.get(key);
        if(previousValue.length() != 0) {
            previousValue.append(' ');
        }
        builder.put(key, previousValue.append(value));
    }

    @Override
    public void putAnalyzer(String analyzerName, String property, Object value) {
        if(!settings.containsKey(ANALYSIS_KEY)) {
            Map<String, Map<String, Map<String, Object>>> analysis = new HashMap<String, Map<String, Map<String, Object>>>();
            settings.put(ANALYSIS_KEY, analysis);
        }

        if(!settings.get(ANALYSIS_KEY).containsKey(ANALYZER_KEY)) {
            Map<String, Map<String, Object>> analyzer = new HashMap<String, Map<String, Object>>();
            settings.get(ANALYSIS_KEY).put(ANALYZER_KEY, analyzer);
        }

        if(!settings.get(ANALYSIS_KEY).get(ANALYZER_KEY).containsKey(analyzerName)) {
            Map<String, Object> newAnalyzer = new HashMap<String, Object>();
            settings.get(ANALYSIS_KEY).get(ANALYZER_KEY).put(analyzerName, newAnalyzer);
        }

        Map<String, Object> analyzer = settings.get(ANALYSIS_KEY).get(ANALYZER_KEY).get(analyzerName);
        analyzer.put(property, value);
    }

    @Override
    public void putMapping(String field, String property, String value) {
        if(!mappings.containsKey(PROPERTIES_KEY)) {
            Map<String, Map<String, String>> properties = new HashMap<>();
            mappings.put(PROPERTIES_KEY, properties);
        }

        if(!mappings.get(PROPERTIES_KEY).containsKey(field)) {
            Map<String, String> newKey = new HashMap<>();
            mappings.get(PROPERTIES_KEY).put(field, newKey);
        }

        Map<String, String> mapping = mappings.get(PROPERTIES_KEY).get(field);
        mapping.put(property, value);
    }

    @Override
    public Map<String, String> getIndexAsMap() {
        Map<String, String> index = new HashMap<String, String>();
        Set<String> keys = builder.keySet();

        for(String key: keys) {
            index.put(key, builder.get(key).toString());
        }

        return index;
    }

    @Override
    public Map<String, Object> getMappings() {
        return new HashMap<String, Object>(mappings);
    }

    @Override
    public Map<String, Object> getSettings() {
        return new HashMap<String, Object>(settings);
    }
}
