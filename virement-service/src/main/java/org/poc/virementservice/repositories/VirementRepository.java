package org.poc.virementservice.repositories;

import org.poc.virementservice.entities.Virement;
import org.poc.virementservice.enums.StatutVirement;
import org.poc.virementservice.enums.TypeVirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface VirementRepository extends JpaRepository<Virement, Long> {

    List<Virement> findByBeneficiaireId(Long beneficiaireId);

    List<Virement> findByRibSource(String ribSource);

    List<Virement> findByTypeVirement(TypeVirement typeVirement);

    List<Virement> findByStatut(StatutVirement statut);

    List<Virement> findByDateVirementBetween(LocalDateTime debut, LocalDateTime fin);

    @Query("SELECT v FROM Virement v WHERE v.ribSource = :rib AND v.dateVirement BETWEEN :debut AND :fin")
    List<Virement> findVirementsByRibAndPeriode(
            @Param("rib") String rib,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin
    );

    @Query("SELECT SUM(v.montant) FROM Virement v WHERE v.ribSource = :rib AND v.statut = 'VALIDE'")
    Double calculerMontantTotalVire(@Param("rib") String rib);
}