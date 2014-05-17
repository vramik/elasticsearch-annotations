package com.github.holmistr.esannotations.indexing.builder;

import java.util.Map;

/**
 * Abstraction of the storage that is used to build index.
 *
 * @author Jiří Holuša
 */
public interface Builder {

    /**
     * Inserts new entry into builder.
     *
     * @param key
     * @param value
     */
    public void put(String key, String value);

    /**
     * TODO: document this
     *
     * @param field
     * @param property
     * @param value
     */
    public void putMapping(String field, String property, String value);

    /**
     * TODO: document this
     *
     * @param analyzerName
     * @param property
     * @param value
     */
    public void putAnalyzer(String analyzerName, String property, Object value);

    /**
     * Return content of builder as Map<String, String>.
     *
     * @return
     */
    public Map<String, String> getIndexAsMap();

    /**
     * TODO document this
     * @return
     */
    public Map<String, Object> getMappings();

    /**
     * TODO document this
     * @return
     */
    public Map<String, Object> getSettings();
}
