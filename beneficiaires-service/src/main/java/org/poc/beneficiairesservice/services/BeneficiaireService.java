package org.poc.beneficiairesservice.services;

import org.poc.beneficiairesservice.dtos.BeneficiaireRequestDTO;
import org.poc.beneficiairesservice.entities.Beneficiaire;
import org.poc.beneficiairesservice.enums.TypeBeneficiaire;

import java.util.List;

import java.util.List;

public interface BeneficiaireService {

    Beneficiaire creerBeneficiaire(BeneficiaireRequestDTO requestDTO);

    Beneficiaire modifierBeneficiaire(Long id, BeneficiaireRequestDTO requestDTO);

    void supprimerBeneficiaire(Long id);

    Beneficiaire getBeneficiaireById(Long id);

    List<Beneficiaire> getAllBeneficiaires();

    List<Beneficiaire> getBeneficiairesByClientId(String clientId);

    List<Beneficiaire> getBeneficiairesActifsByClientId(String clientId);

    List<Beneficiaire> getBeneficiairesByClientIdAndType(String clientId, TypeBeneficiaire type);

    List<Beneficiaire> searchBeneficiaires(String clientId, String search);

    Beneficiaire desactiverBeneficiaire(Long id);

    Beneficiaire activerBeneficiaire(Long id);

    long countBeneficiairesActifs(String clientId);
}
