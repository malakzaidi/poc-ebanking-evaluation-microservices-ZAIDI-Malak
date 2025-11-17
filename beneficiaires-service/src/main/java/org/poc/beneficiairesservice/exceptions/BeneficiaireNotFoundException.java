package org.poc.beneficiairesservice.exceptions;

public class BeneficiaireNotFoundException extends RuntimeException {
    public BeneficiaireNotFoundException(String message) {
        super(message);
    }
}