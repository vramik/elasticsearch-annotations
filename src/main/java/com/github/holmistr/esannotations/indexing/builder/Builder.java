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
     * Return content of builder as Map<String, String>.
     *
     * @return
     */
    public Map<String, String> getIndexAsMap();
}
