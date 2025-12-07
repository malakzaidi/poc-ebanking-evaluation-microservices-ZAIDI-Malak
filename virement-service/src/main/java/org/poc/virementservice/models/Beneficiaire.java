package org.poc.virementservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modèle représentant un bénéficiaire du service beneficiaires-service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiaire {
    private Long id;
    private String nom;
    private String prenom;
    private String rib;
    private String typeBeneficiaire; // PHYSIQUE ou MORALE
}