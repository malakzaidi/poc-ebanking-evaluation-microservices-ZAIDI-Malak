package org.poc.beneficiairesservice.exceptions;

public class RibAlreadyExistsException extends RuntimeException {
    public RibAlreadyExistsException(String message) {
        super(message);
    }
}