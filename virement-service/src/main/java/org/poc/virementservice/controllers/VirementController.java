package org.poc.virementservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.poc.virementservice.dtos.VirementDTO;
import org.poc.virementservice.entities.Virement;
import org.poc.virementservice.enums.StatutVirement;
import org.poc.virementservice.enums.TypeVirement;
import org.poc.virementservice.services.VirementService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/virements")
@RequiredArgsConstructor
@Tag(name = "Virement", description = "API de gestion des virements bancaires")
@CrossOrigin(origins = "*")
public class VirementController {

    private final VirementService virementService;

    @PostMapping
    @Operation(summary = "Créer un nouveau virement", description = "Permet de créer un virement bancaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Virement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Virement> creerVirement(@Valid @RequestBody VirementDTO virementDTO) {
        Virement virement = virementService.creerVirement(virementDTO);
        return new ResponseEntity<>(virement, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un virement par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Virement trouvé"),
            @ApiResponse(responseCode = "404", description = "Virement non trouvé")
    })
    public ResponseEntity<Virement> getVirementById(
            @Parameter(description = "ID du virement") @PathVariable Long id) {
        return ResponseEntity.ok(virementService.getVirementById(id));
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les virements")
    public ResponseEntity<List<Virement>> getAllVirements() {
        return ResponseEntity.ok(virementService.getAllVirements());
    }

    @GetMapping("/beneficiaire/{beneficiaireId}")
    @Operation(summary = "Récupérer les virements d'un bénéficiaire")
    public ResponseEntity<List<Virement>> getVirementsByBeneficiaire(
            @Parameter(description = "ID du bénéficiaire") @PathVariable Long beneficiaireId) {
        return ResponseEntity.ok(virementService.getVirementsByBeneficiaire(beneficiaireId));
    }

    @GetMapping("/rib/{ribSource}")
    @Operation(summary = "Récupérer les virements par RIB source")
    public ResponseEntity<List<Virement>> getVirementsByRibSource(
            @Parameter(description = "RIB source") @PathVariable String ribSource) {
        return ResponseEntity.ok(virementService.getVirementsByRibSource(ribSource));
    }

    @GetMapping("/type/{typeVirement}")
    @Operation(summary = "Récupérer les virements par type")
    public ResponseEntity<List<Virement>> getVirementsByType(
            @Parameter(description = "Type de virement (NORMAL ou INSTANTANE)")
            @PathVariable TypeVirement typeVirement) {
        return ResponseEntity.ok(virementService.getVirementsByType(typeVirement));
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Récupérer les virements par statut")
    public ResponseEntity<List<Virement>> getVirementsByStatut(
            @Parameter(description = "Statut du virement")
            @PathVariable StatutVirement statut) {
        return ResponseEntity.ok(virementService.getVirementsByStatut(statut));
    }

    @GetMapping("/periode")
    @Operation(summary = "Récupérer les virements par période")
    public ResponseEntity<List<Virement>> getVirementsByPeriode(
            @Parameter(description = "Date de début (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @Parameter(description = "Date de fin (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(virementService.getVirementsByPeriode(debut, fin));
    }

    @PutMapping("/{id}/valider")
    @Operation(summary = "Valider un virement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Virement validé"),
            @ApiResponse(responseCode = "400", description = "Virement ne peut pas être validé")
    })
    public ResponseEntity<Virement> validerVirement(
            @Parameter(description = "ID du virement") @PathVariable Long id) {
        return ResponseEntity.ok(virementService.validerVirement(id));
    }

    @PutMapping("/{id}/refuser")
    @Operation(summary = "Refuser un virement")
    public ResponseEntity<Virement> refuserVirement(
            @Parameter(description = "ID du virement") @PathVariable Long id) {
        return ResponseEntity.ok(virementService.refuserVirement(id));
    }

    @PutMapping("/{id}/annuler")
    @Operation(summary = "Annuler un virement")
    public ResponseEntity<Virement> annulerVirement(
            @Parameter(description = "ID du virement") @PathVariable Long id) {
        return ResponseEntity.ok(virementService.annulerVirement(id));
    }

    @GetMapping("/rib/{ribSource}/montant-total")
    @Operation(summary = "Calculer le montant total viré depuis un RIB")
    public ResponseEntity<Map<String, Double>> calculerMontantTotal(
            @Parameter(description = "RIB source") @PathVariable String ribSource) {
        Double montantTotal = virementService.calculerMontantTotalVire(ribSource);
        return ResponseEntity.ok(Map.of("montantTotal", montantTotal));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un virement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Virement supprimé"),
            @ApiResponse(responseCode = "404", description = "Virement non trouvé")
    })
    public ResponseEntity<Void> deleteVirement(
            @Parameter(description = "ID du virement") @PathVariable Long id) {
        virementService.deleteVirement(id);
        return ResponseEntity.noContent().build();
    }
}