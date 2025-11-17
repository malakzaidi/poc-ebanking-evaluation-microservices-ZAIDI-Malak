package org.poc.beneficiairesservice.enums;

public enum TypeBeneficiaire {
    PHYSIQUE("Personne Physique"),
    MORALE("Personne Morale");

    private final String description;

    TypeBeneficiaire(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}