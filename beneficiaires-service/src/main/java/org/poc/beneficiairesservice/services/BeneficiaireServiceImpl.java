package org.poc.beneficiairesservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.poc.beneficiairesservice.dtos.BeneficiaireRequestDTO;
import org.poc.beneficiairesservice.entities.Beneficiaire;
import org.poc.beneficiairesservice.enums.TypeBeneficiaire;
import org.poc.beneficiairesservice.exceptions.BeneficiaireNotFoundException;
import org.poc.beneficiairesservice.exceptions.RibAlreadyExistsException;
import org.poc.beneficiairesservice.mappers.BeneficiaireMapper;
import org.poc.beneficiairesservice.repository.BeneficiaireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class BeneficiaireServiceImpl implements BeneficiaireService {

    private final BeneficiaireRepository beneficiaireRepository;
    private final BeneficiaireMapper beneficiaireMapper;

    @Override
    public Beneficiaire creerBeneficiaire(BeneficiaireRequestDTO requestDTO) {
        log.info("Création d'un nouveau bénéficiaire pour le client: {}", requestDTO.getClientId());

        // Vérifier si le RIB existe déjà
        if (beneficiaireRepository.existsByRib(requestDTO.getRib())) {
            throw new RibAlreadyExistsException("Un bénéficiaire avec ce RIB existe déjà");
        }

        // Utilisation du mapper pour convertir DTO -> Entity
        Beneficiaire beneficiaire = beneficiaireMapper.toEntity(requestDTO);

        Beneficiaire saved = beneficiaireRepository.save(beneficiaire);
        log.info("Bénéficiaire créé avec succès, ID: {}", saved.getId());

        return saved;
    }

    @Override
    public Beneficiaire modifierBeneficiaire(Long id, BeneficiaireRequestDTO requestDTO) {
        log.info("Modification du bénéficiaire ID: {}", id);

        Beneficiaire beneficiaire = getBeneficiaireById(id);

        // Vérifier si le nouveau RIB existe déjà pour un autre bénéficiaire
        if (!beneficiaire.getRib().equals(requestDTO.getRib()) &&
                beneficiaireRepository.existsByRibAndIdNot(requestDTO.getRib(), id)) {
            throw new RibAlreadyExistsException("Un autre bénéficiaire avec ce RIB existe déjà");
        }

        // Utilisation du mapper pour mettre à jour l'entité
        beneficiaireMapper.updateEntityFromDTO(requestDTO, beneficiaire);

        Beneficiaire updated = beneficiaireRepository.save(beneficiaire);
        log.info("Bénéficiaire modifié avec succès, ID: {}", id);

        return updated;
    }

    @Override
    public void supprimerBeneficiaire(Long id) {
        log.info("Suppression du bénéficiaire ID: {}", id);

        Beneficiaire beneficiaire = getBeneficiaireById(id);
        beneficiaireRepository.delete(beneficiaire);

        log.info("Bénéficiaire supprimé avec succès, ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Beneficiaire getBeneficiaireById(Long id) {
        return beneficiaireRepository.findById(id)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Bénéficiaire non trouvé avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beneficiaire> getAllBeneficiaires() {
        log.info("Récupération de tous les bénéficiaires");
        return beneficiaireRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beneficiaire> getBeneficiairesByClientId(String clientId) {
        log.info("Récupération des bénéficiaires pour le client: {}", clientId);
        return beneficiaireRepository.findByClientId(clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beneficiaire> getBeneficiairesActifsByClientId(String clientId) {
        log.info("Récupération des bénéficiaires actifs pour le client: {}", clientId);
        return beneficiaireRepository.findByClientIdAndActifTrue(clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beneficiaire> getBeneficiairesByClientIdAndType(String clientId, TypeBeneficiaire type) {
        log.info("Récupération des bénéficiaires de type {} pour le client: {}", type, clientId);
        return beneficiaireRepository.findByClientIdAndType(clientId, type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beneficiaire> searchBeneficiaires(String clientId, String search) {
        log.info("Recherche de bénéficiaires pour le client {} avec le terme: {}", clientId, search);
        return beneficiaireRepository.searchByClientIdAndNomOrPrenom(clientId, search);
    }

    @Override
    public Beneficiaire desactiverBeneficiaire(Long id) {
        log.info("Désactivation du bénéficiaire ID: {}", id);

        Beneficiaire beneficiaire = getBeneficiaireById(id);
        beneficiaire.setActif(false);

        return beneficiaireRepository.save(beneficiaire);
    }

    @Override
    public Beneficiaire activerBeneficiaire(Long id) {
        log.info("Activation du bénéficiaire ID: {}", id);

        Beneficiaire beneficiaire = getBeneficiaireById(id);
        beneficiaire.setActif(true);

        return beneficiaireRepository.save(beneficiaire);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBeneficiairesActifs(String clientId) {
        return beneficiaireRepository.countByClientIdAndActifTrue(clientId);
    }
}