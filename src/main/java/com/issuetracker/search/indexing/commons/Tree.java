package com.issuetracker.search.indexing.commons;

/**
 * //TODO: document this
 *
 * @author Jiří Holuša
 */
public interface Tree<T> {

    public Integer add(T value, T parent, Integer branchId);

}
