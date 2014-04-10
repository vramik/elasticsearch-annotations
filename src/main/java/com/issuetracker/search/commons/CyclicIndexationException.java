package com.issuetracker.search.commons;

/**
 * Exception that signal cyclic indexation.
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
