package org.poc.beneficiairesservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poc.beneficiairesservice.enums.TypeBeneficiaire;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaireRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    private String prenom;

    @NotBlank(message = "Le RIB est obligatoire")
    @Pattern(regexp = "^[0-9]{24}$", message = "Le RIB doit contenir exactement 24 chiffres")
    private String rib;

    @NotNull(message = "Le type de bénéficiaire est obligatoire")
    private TypeBeneficiaire type;

    @NotBlank(message = "L'identifiant client est obligatoire")
    private String clientId;
}

