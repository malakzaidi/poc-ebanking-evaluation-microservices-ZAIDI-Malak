package org.poc.virementservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;;
import org.poc.virementservice.entities.Virement;
import org.poc.virementservice.enums.StatutVirement;
import org.poc.virementservice.enums.TypeVirement;
import org.poc.virementservice.repositories.VirementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final VirementRepository virementRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Chargement des données de test...");

        // Créer des virements de test
        Virement virement1 = Virement.builder()
                .beneficiaireId(1L)
                .ribSource("123456789012345678901234")
                .montant(5000.0)
                .description("Virement de salaire")
                .typeVirement(TypeVirement.NORMAL)
                .dateVirement(LocalDateTime.now().minusDays(5))
                .statut(StatutVirement.VALIDE)
                .build();

        Virement virement2 = Virement.builder()
                .beneficiaireId(2L)
                .ribSource("123456789012345678901234")
                .montant(1500.0)
                .description("Paiement facture")
                .typeVirement(TypeVirement.INSTANTANE)
                .dateVirement(LocalDateTime.now().minusDays(2))
                .statut(StatutVirement.VALIDE)
                .build();

        Virement virement3 = Virement.builder()
                .beneficiaireId(1L)
                .ribSource("987654321098765432109876")
                .montant(2500.0)
                .description("Virement loyer")
                .typeVirement(TypeVirement.NORMAL)
                .dateVirement(LocalDateTime.now().minusDays(1))
                .statut(StatutVirement.EN_ATTENTE)
                .build();

        Virement virement4 = Virement.builder()
                .beneficiaireId(3L)
                .ribSource("123456789012345678901234")
                .montant(750.0)
                .description("Remboursement")
                .typeVirement(TypeVirement.INSTANTANE)
                .dateVirement(LocalDateTime.now())
                .statut(StatutVirement.EN_ATTENTE)
                .build();

        Virement virement5 = Virement.builder()
                .beneficiaireId(2L)
                .ribSource("555555555555555555555555")
                .montant(3000.0)
                .description("Achat équipement")
                .typeVirement(TypeVirement.NORMAL)
                .dateVirement(LocalDateTime.now().minusDays(10))
                .statut(StatutVirement.REFUSE)
                .build();

        virementRepository.saveAll(Arrays.asList(
                virement1, virement2, virement3, virement4, virement5
        ));

        log.info("Données de test chargées avec succès: {} virements créés",
                virementRepository.count());

        // Afficher les statistiques
        log.info("Virements validés: {}",
                virementRepository.findByStatut(StatutVirement.VALIDE).size());
        log.info("Virements en attente: {}",
                virementRepository.findByStatut(StatutVirement.EN_ATTENTE).size());
        log.info("Virements refusés: {}",
                virementRepository.findByStatut(StatutVirement.REFUSE).size());
    }
}