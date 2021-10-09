package com.dealstop.mvcEcommerce.exceptions;

/**
 * Exception thrown when an entity is not found
 */

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
