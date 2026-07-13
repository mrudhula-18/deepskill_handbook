package com.dn5.week3.rest.exception;

/**
 * Thrown when a requested resource does not exist. Translated to an
 * HTTP 404 response by {@link GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
