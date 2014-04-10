package com.github.holmistr.esannotations.commons;

/**
 * Simple interface for trees.
 *
 * @author Jiří Holuša
 */
public interface Tree<T> {

    /**
     * Adds value to specific branch as a child of specific node.
     *
     * @param value
     * @param parent
     * @param branchId branch to be inserted to
     * @return ID of the current branch
     */
    public Integer add(T value, T parent, Integer branchId);

}
