package com.issuetracker.search.indexing;

import java.util.Map;

/**
 * Interface of the indexation mechanism.
 *
 * @author Jiří Holuša
 */
public interface Indexer {

    /**
     * Same as index(entity, "")
     *
     * @param entity
     */
    public void index(Object entity);

    /**
     * Creates index of the entity with provided prefix that is added to
     * every key of the index.
     *
     * @param entity entity to be indexed
     * @param prefix prefix to be added
     */
    public void index(Object entity, String prefix);

    /**
     * Return the index of the entity that was indexed as Map.
     *
     * @return
     */
    public Map<String, String> getIndexOfSingleEntityAsMap();

    /**
     * Return the indexes of all entities that was affected by indexation
     * of entity and therefore their index has to be rebuilt, for example because of
     * existence of relationship or being embedded.
     *
     * @return Map of all entities and their new indexes. Keys are the entities, values
     * are indexes of those entities.
     */
    public Map<Object, Map<String, String>> getCompleteIndexChanges();

}
