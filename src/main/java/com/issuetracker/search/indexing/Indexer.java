package com.issuetracker.search.indexing;

import java.util.Map;

/**
 * // TODO: Document this
 *
 * @author Jiří Holuša
 */
public interface Indexer {

    public void index(Object entity);

    public void index(Object entity, String prefix);

    public Map<String, String> getIndexOfSingleEntityAsMap();

    public Map<Object, Map<String, String>> getCompleteIndexChanges();

}
