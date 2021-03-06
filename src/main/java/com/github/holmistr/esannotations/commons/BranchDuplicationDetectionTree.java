package com.github.holmistr.esannotations.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Tree interface. It does not allow to insert the
 * same instance into the same branch.
 *
 * @author Jiří Holuša
 */
public class BranchDuplicationDetectionTree<T> implements Tree<T> {

    private Node<T> root;
    private int branchCounter = 0;

    /**
     * @throws IllegalStateException if the object is already present in the branch
     */
    @Override
    public Integer add(T value, T parent, Integer branchId) {
        if(root == null) {
            root = new Node<T>();
            root.setValue(value);

            return null;
        }

        if(branchId == null && parent.equals(root.getValue())) {
            if(root.getValue().equals(value)) {
                throw new IllegalStateException("Object is already present as root.");
            }

            root.addChild(value, branchCounter);
            branchCounter++;

            return (branchCounter - 1);
        }

        Integer returnValue = null;
        Node<T> iterationNode = root;
        while(iterationNode != null) {
            if(iterationNode.getValue().equals(value)) {
                throw new IllegalStateException("Object is already present in the current branch.");
            }

            if(iterationNode.hasChildOnBranch(branchId) && !parent.equals(iterationNode.getValue())) {
                iterationNode = iterationNode.getChildOnBranch(branchId);
            }
            else if(parent.equals(iterationNode.getValue()) && iterationNode.hasChildren()) {
                iterationNode.addChild(value, branchCounter);
                fixTree(iterationNode, branchCounter);

                returnValue = branchCounter;
                branchCounter++;
                break;
            }
            else {
                iterationNode.addChild(value, branchId);
                returnValue = branchId;
                break;
            }
        }

        return returnValue;
    }

    private void fixTree(Node<T> startingNode, int branchId) {
        Node<T> parentNode = startingNode.getParent();
        Node<T> iterationNode = startingNode;
        while(parentNode != null) {
            parentNode.addChild(iterationNode, branchId);

            iterationNode = parentNode;
            parentNode = parentNode.getParent();
        }
    }

    /**
     * Internal representation of node.
     * @param <T>
     */
    class Node<T> {

        private T value;
        private Map<Integer, Node<T>> children = new HashMap<Integer, Node<T>>();
        private Node<T> parent;

        void addChild(T value, int branchId) {
            Node<T> newNode = new Node<T>();
            newNode.setValue(value);
            newNode.setParent(this);

            children.put(branchId, newNode);
        }

        void addChild(Node<T> node, int branchId) {
            children.put(branchId, node);
        }

        void setValue(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }

        Node<T> getParent() {
            return parent;
        }

        void setParent(Node<T> parent) {
            this.parent = parent;
        }

        boolean hasChildren() {
            return !children.isEmpty();
        }

        boolean hasChildOnBranch(int branchId) {
            return children.containsKey(branchId);
        }

        Node<T> getChildOnBranch(int branchId) {
            return children.get(branchId);
        }
    }
}
