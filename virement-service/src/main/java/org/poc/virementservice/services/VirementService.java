package org.poc.virementservice.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.poc.virementservice.dtos.VirementDTO;
import org.poc.virementservice.entities.Virement;
import org.poc.virementservice.enums.StatutVirement;
import org.poc.virementservice.repositories.VirementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VirementService {

    private final VirementRepository virementRepository;

    public Virement creerVirement(VirementDTO virementDTO) {
        log.info("Création d'un virement pour le bénéficiaire ID: {}", virementDTO.getBeneficiaireId());

        Virement virement = Virement.builder()
                .beneficiaireId(virementDTO.getBeneficiaireId())
                .ribSource(virementDTO.getRibSource())
                .montant(virementDTO.getMontant())
                .description(virementDTO.getDescription())
                .typeVirement(virementDTO.getTypeVirement())
                .dateVirement(LocalDateTime.now())
                .statut(StatutVirement.EN_ATTENTE)
                .build();

        Virement virementSaved = virementRepository.save(virement);
        log.info("Virement créé avec succès, ID: {}", virementSaved.getId());

        return virementSaved;
    }

    public Virement getVirementById(Long id) {
        log.info("Récupération du virement ID: {}", id);
        return virementRepository.findById(id)
                .orElseThrow(() -> new VirementNotFoundException("Virement non trouvé avec l'ID: " + id));
    }

    public List<Virement> getAllVirements() {
        log.info("Récupération de tous les virements");
        return virementRepository.findAll();
    }

    public List<Virement> getVirementsByBeneficiaire(Long beneficiaireId) {
        log.info("Récupération des virements pour le bénéficiaire ID: {}", beneficiaireId);
        return virementRepository.findByBeneficiaireId(beneficiaireId);
    }

    public List<Virement> getVirementsByRibSource(String ribSource) {
        log.info("Récupération des virements pour le RIB: {}", ribSource);
        return virementRepository.findByRibSource(ribSource);
    }

    public List<Virement> getVirementsByType(TypeVirement typeVirement) {
        log.info("Récupération des virements de type: {}", typeVirement);
        return virementRepository.findByTypeVirement(typeVirement);
    }

    public List<Virement> getVirementsByStatut(StatutVirement statut) {
        log.info("Récupération des virements avec le statut: {}", statut);
        return virementRepository.findByStatut(statut);
    }

    public List<Virement> getVirementsByPeriode(LocalDateTime debut, LocalDateTime fin) {
        log.info("Récupération des virements entre {} et {}", debut, fin);
        return virementRepository.findByDateVirementBetween(debut, fin);
    }

    public Virement validerVirement(Long id) {
        log.info("Validation du virement ID: {}", id);
        Virement virement = getVirementById(id);

        if (virement.getStatut() != StatutVirement.EN_ATTENTE) {
            throw new IllegalStateException("Seuls les virements en attente peuvent être validés");
        }

        virement.setStatut(StatutVirement.VALIDE);
        Virement virementUpdated = virementRepository.save(virement);
        log.info("Virement validé avec succès, ID: {}", id);

        return virementUpdated;
    }

    public Virement refuserVirement(Long id) {
        log.info("Refus du virement ID: {}", id);
        Virement virement = getVirementById(id);

        if (virement.getStatut() != StatutVirement.EN_ATTENTE) {
            throw new IllegalStateException("Seuls les virements en attente peuvent être refusés");
        }

        virement.setStatut(StatutVirement.REFUSE);
        Virement virementUpdated = virementRepository.save(virement);
        log.info("Virement refusé avec succès, ID: {}", id);

        return virementUpdated;
    }

    public Virement annulerVirement(Long id) {
        log.info("Annulation du virement ID: {}", id);
        Virement virement = getVirementById(id);

        if (virement.getStatut() == StatutVirement.VALIDE) {
            throw new IllegalStateException("Un virement validé ne peut pas être annulé");
        }

        virement.setStatut(StatutVirement.ANNULE);
        Virement virementUpdated = virementRepository.save(virement);
        log.info("Virement annulé avec succès, ID: {}", id);

        return virementUpdated;
    }

    public Double calculerMontantTotalVire(String ribSource) {
        log.info("Calcul du montant total viré pour le RIB: {}", ribSource);
        Double total = virementRepository.calculerMontantTotalVire(ribSource);
        return total != null ? total : 0.0;
    }

    public void deleteVirement(Long id) {
        log.info("Suppression du virement ID: {}", id);
        Virement virement = getVirementById(id);
        virementRepository.delete(virement);
        log.info("Virement supprimé avec succès, ID: {}", id);
    }
}