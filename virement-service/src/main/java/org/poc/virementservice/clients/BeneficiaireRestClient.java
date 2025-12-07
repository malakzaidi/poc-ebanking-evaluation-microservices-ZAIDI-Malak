package org.poc.virementservice.clients;

import org.poc.virementservice.models.Beneficiaire;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Client Feign pour communiquer avec le micro-service beneficiaires-service
 */
@FeignClient(name = "beneficiaires-service")
public interface BeneficiaireRestClient {

    @GetMapping("/api/beneficiaires/{id}")
    Optional<Beneficiaire> getBeneficiaireById(@PathVariable Long id);

    @GetMapping("/api/beneficiaires/rib/{rib}")
    Optional<Beneficiaire> getBeneficiaireByRib(@PathVariable String rib);
}