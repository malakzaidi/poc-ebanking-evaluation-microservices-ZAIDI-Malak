package org.poc.virementservice.exceptions;

public class VirementNotFoundException extends RuntimeException {
    public VirementNotFoundException(String message) {
        super(message);
    }
}