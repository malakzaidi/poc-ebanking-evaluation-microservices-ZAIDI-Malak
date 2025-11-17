package org.poc.beneficiairesservice.repository;

import org.poc.beneficiairesservice.entities.Beneficiaire;
import org.poc.beneficiairesservice.enums.TypeBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {

    // Trouver tous les bénéficiaires d'un client
    List<Beneficiaire> findByClientId(String clientId);

    // Trouver les bénéficiaires actifs d'un client
    List<Beneficiaire> findByClientIdAndActifTrue(String clientId);

    // Trouver par RIB
    Optional<Beneficiaire> findByRib(String rib);

    // Vérifier si un RIB existe déjà
    boolean existsByRib(String rib);

    // Vérifier si un RIB existe pour un autre bénéficiaire (utile pour l'update)
    boolean existsByRibAndIdNot(String rib, Long id);

    // Trouver par type de bénéficiaire
    List<Beneficiaire> findByClientIdAndType(String clientId, TypeBeneficiaire type);

    // Recherche par nom ou prénom (insensible à la casse)
    @Query("SELECT b FROM Beneficiaire b WHERE b.clientId = :clientId AND " +
            "(LOWER(b.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(b.prenom) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Beneficiaire> searchByClientIdAndNomOrPrenom(
            @Param("clientId") String clientId,
            @Param("search") String search
    );

    // Compter les bénéficiaires actifs d'un client
    long countByClientIdAndActifTrue(String clientId);
}