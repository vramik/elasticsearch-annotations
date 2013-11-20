package com.issuetracker.search.indexing.api;

import com.issuetracker.search.indexing.annotations.IndexedEntity;

/**
 * // TODO: Document this
 *
 * @author jholusa
 * @since 4.0
 */
public interface Indexer {

    public void  index(Object entity);

}
