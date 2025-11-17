package org.poc.beneficiairesservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.poc.beneficiairesservice.dtos.BeneficiaireRequestDTO;
import org.poc.beneficiairesservice.entities.Beneficiaire;
import org.poc.beneficiairesservice.enums.TypeBeneficiaire;
import org.poc.beneficiairesservice.services.BeneficiaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/beneficiaires")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Bénéficiaires", description = "API de gestion des bénéficiaires")
public class BeneficiaireController {

    private final BeneficiaireService beneficiaireService;

    @Operation(summary = "Créer un nouveau bénéficiaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bénéficiaire créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "RIB déjà existant")
    })
    @PostMapping
    public ResponseEntity<Beneficiaire> creerBeneficiaire(
            @Valid @RequestBody BeneficiaireRequestDTO requestDTO) {

        Beneficiaire beneficiaire = beneficiaireService.creerBeneficiaire(requestDTO);
        return new ResponseEntity<>(beneficiaire, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifier un bénéficiaire existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bénéficiaire modifié avec succès"),
            @ApiResponse(responseCode = "404", description = "Bénéficiaire non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "RIB déjà existant")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Beneficiaire> modifierBeneficiaire(
            @Parameter(description = "ID du bénéficiaire") @PathVariable Long id,
            @Valid @RequestBody BeneficiaireRequestDTO requestDTO) {

        Beneficiaire beneficiaire = beneficiaireService.modifierBeneficiaire(id, requestDTO);
        return ResponseEntity.ok(beneficiaire);
    }

    @Operation(summary = "Supprimer un bénéficiaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bénéficiaire supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Bénéficiaire non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> supprimerBeneficiaire(
            @Parameter(description = "ID du bénéficiaire") @PathVariable Long id) {

        beneficiaireService.supprimerBeneficiaire(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Bénéficiaire supprimé avec succès");
        response.put("id", id.toString());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtenir un bénéficiaire par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bénéficiaire trouvé"),
            @ApiResponse(responseCode = "404", description = "Bénéficiaire non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Beneficiaire> getBeneficiaireById(
            @Parameter(description = "ID du bénéficiaire") @PathVariable Long id) {

        Beneficiaire beneficiaire = beneficiaireService.getBeneficiaireById(id);
        return ResponseEntity.ok(beneficiaire);
    }

    @Operation(summary = "Obtenir tous les bénéficiaires")
    @GetMapping
    public ResponseEntity<List<Beneficiaire>> getAllBeneficiaires() {
        List<Beneficiaire> beneficiaires = beneficiaireService.getAllBeneficiaires();
        return ResponseEntity.ok(beneficiaires);
    }

    @Operation(summary = "Obtenir les bénéficiaires d'un client")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Beneficiaire>> getBeneficiairesByClient(
            @Parameter(description = "ID du client") @PathVariable String clientId,
            @Parameter(description = "Filtrer uniquement les actifs")
            @RequestParam(required = false, defaultValue = "false") boolean actifsOnly) {

        List<Beneficiaire> beneficiaires;

        if (actifsOnly) {
            beneficiaires = beneficiaireService.getBeneficiairesActifsByClientId(clientId);
        } else {
            beneficiaires = beneficiaireService.getBeneficiairesByClientId(clientId);
        }

        return ResponseEntity.ok(beneficiaires);
    }

    @Operation(summary = "Obtenir les bénéficiaires d'un client par type")
    @GetMapping("/client/{clientId}/type/{type}")
    public ResponseEntity<List<Beneficiaire>> getBeneficiairesByClientAndType(
            @Parameter(description = "ID du client") @PathVariable String clientId,
            @Parameter(description = "Type de bénéficiaire") @PathVariable TypeBeneficiaire type) {

        List<Beneficiaire> beneficiaires =
                beneficiaireService.getBeneficiairesByClientIdAndType(clientId, type);
        return ResponseEntity.ok(beneficiaires);
    }

    @Operation(summary = "Rechercher des bénéficiaires par nom ou prénom")
    @GetMapping("/client/{clientId}/search")
    public ResponseEntity<List<Beneficiaire>> searchBeneficiaires(
            @Parameter(description = "ID du client") @PathVariable String clientId,
            @Parameter(description = "Terme de recherche") @RequestParam String query) {

        List<Beneficiaire> beneficiaires =
                beneficiaireService.searchBeneficiaires(clientId, query);
        return ResponseEntity.ok(beneficiaires);
    }

    @Operation(summary = "Désactiver un bénéficiaire")
    @PatchMapping("/{id}/desactiver")
    public ResponseEntity<Beneficiaire> desactiverBeneficiaire(
            @Parameter(description = "ID du bénéficiaire") @PathVariable Long id) {

        Beneficiaire beneficiaire = beneficiaireService.desactiverBeneficiaire(id);
        return ResponseEntity.ok(beneficiaire);
    }

    @Operation(summary = "Activer un bénéficiaire")
    @PatchMapping("/{id}/activer")
    public ResponseEntity<Beneficiaire> activerBeneficiaire(
            @Parameter(description = "ID du bénéficiaire") @PathVariable Long id) {

        Beneficiaire beneficiaire = beneficiaireService.activerBeneficiaire(id);
        return ResponseEntity.ok(beneficiaire);
    }

    @Operation(summary = "Compter les bénéficiaires actifs d'un client")
    @GetMapping("/client/{clientId}/count")
    public ResponseEntity<Map<String, Long>> countBeneficiairesActifs(
            @Parameter(description = "ID du client") @PathVariable String clientId) {

        long count = beneficiaireService.countBeneficiairesActifs(clientId);

        Map<String, Long> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }
}