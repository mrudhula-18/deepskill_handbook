package com.dn5.capstone.bookstore.exception;

/**
 * Thrown when a requested entity (Book, Author, Category, ...) cannot be found by id.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forEntity(String entityName, Long id) {
        return new ResourceNotFoundException(entityName + " not found with id: " + id);
    }
}
