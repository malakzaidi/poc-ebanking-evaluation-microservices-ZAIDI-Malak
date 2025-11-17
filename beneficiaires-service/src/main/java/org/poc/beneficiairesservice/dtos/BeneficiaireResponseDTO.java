package org.poc.beneficiairesservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poc.beneficiairesservice.enums.TypeBeneficiaire;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaireResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String rib;
    private String ribMasque; // RIB masqué pour affichage
    private TypeBeneficiaire type;
    private String clientId;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private Boolean actif;
}
