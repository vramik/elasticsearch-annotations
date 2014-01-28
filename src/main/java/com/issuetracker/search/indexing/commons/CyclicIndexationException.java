package com.issuetracker.search.indexing.commons;

/**
 * //TODO: document this
 *
 * @author Jiří Holuša
 */
public class CyclicIndexationException extends RuntimeException {

    public CyclicIndexationException() {
        super();
    }

    public CyclicIndexationException(String message) {
        super(message);
    }

    public CyclicIndexationException(String message, Throwable cause) {
        super(message, cause);
    }
}
