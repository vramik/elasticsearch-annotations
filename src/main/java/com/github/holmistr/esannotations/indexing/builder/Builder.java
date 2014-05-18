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
     * Put mapping for a field.
     *
     * @param field
     * @param property mapping property
     * @param value mapping value
     */
    public void putMapping(String field, String property, String value);

    /**
     * Puts property to an analyzer.
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
     * Returns built mappings.
     * @return
     */
    public Map<String, Object> getMappings();

    /**
     * Returns built settings.
     *
     * @return
     */
    public Map<String, Object> getSettings();
}
