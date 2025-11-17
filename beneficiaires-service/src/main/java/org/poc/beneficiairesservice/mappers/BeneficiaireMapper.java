package org.poc.beneficiairesservice.mappers;


import org.mapstruct.*;
import org.poc.beneficiairesservice.dtos.BeneficiaireRequestDTO;
import org.poc.beneficiairesservice.dtos.BeneficiaireResponseDTO;
import org.poc.beneficiairesservice.entities.Beneficiaire;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BeneficiaireMapper {

    // ========== Conversion Entity -> Response DTO ==========

    /**
     * Convertit une entité Beneficiaire en DTO de réponse
     */
    @Mapping(target = "ribMasque", expression = "java(masquerRib(beneficiaire.getRib()))")
    BeneficiaireResponseDTO toResponseDTO(Beneficiaire beneficiaire);

    /**
     * Convertit une liste d'entités en liste de DTOs de réponse
     */
    List<BeneficiaireResponseDTO> toResponseDTOList(List<Beneficiaire> beneficiaires);

    // ========== Conversion Request DTO -> Entity ==========

    /**
     * Convertit un DTO de requête en entité Beneficiaire (pour création)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actif", constant = "true")
    Beneficiaire toEntity(BeneficiaireRequestDTO requestDTO);

    /**
     * Met à jour une entité existante à partir d'un DTO de requête
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "clientId", ignore = true) // On ne change pas le clientId lors d'une mise à jour
    void updateEntityFromDTO(BeneficiaireRequestDTO requestDTO, @MappingTarget Beneficiaire beneficiaire);

    // ========== Méthodes auxiliaires ==========

    /**
     * Masque partiellement le RIB pour la sécurité
     * Exemple: 123456789012345678901234 -> 1234****************0234
     */
    default String masquerRib(String rib) {
        if (rib == null || rib.length() != 24) {
            return rib;
        }
        return rib.substring(0, 4) + "****************" + rib.substring(20);
    }

    /**
     * Formate le nom complet du bénéficiaire
     */
    @Named("nomComplet")
    default String getNomComplet(Beneficiaire beneficiaire) {
        return beneficiaire.getPrenom() + " " + beneficiaire.getNom();
    }
}
