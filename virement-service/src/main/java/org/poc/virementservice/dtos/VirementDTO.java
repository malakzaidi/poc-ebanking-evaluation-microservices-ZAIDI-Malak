package org.poc.virementservice.dtos;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poc.virementservice.enums.TypeVirement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VirementDTO {

    @NotNull(message = "L'identifiant du bénéficiaire est obligatoire")
    @Positive(message = "L'identifiant du bénéficiaire doit être positif")
    private Long beneficiaireId;

    @NotBlank(message = "Le RIB source est obligatoire")
    @Size(min = 24, max = 24, message = "Le RIB doit contenir exactement 24 caractères")
    @Pattern(regexp = "^[0-9]{24}$", message = "Le RIB doit contenir uniquement des chiffres")
    private String ribSource;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    @DecimalMin(value = "1.0", message = "Le montant minimum est de 1 DH")
    private Double montant;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @NotNull(message = "Le type de virement est obligatoire")
    private TypeVirement typeVirement;
}