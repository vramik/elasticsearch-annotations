package com.issuetracker.search.indexing.builder;

import java.util.Map;

/**
 * //TODO: document this
 *
 * @author Jiří Holuša
 */
public interface Builder {

    public void put(String key, String value);

    public Map<String, String> getIndexAsMap();
}
