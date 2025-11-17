package org.poc.beneficiairesservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;
import org.poc.beneficiairesservice.enums.TypeBeneficiaire;

import java.time.LocalDateTime;

@Entity
@Table(name = "beneficiaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String prenom;

    @NotBlank(message = "Le RIB est obligatoire")
    @Pattern(
            regexp = "^[0-9]{24}$",
            message = "Le RIB doit contenir exactement 24 chiffres"
    )
    @Column(nullable = false, unique = true, length = 24)
    private String rib;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeBeneficiaire type;

    @NotBlank(message = "L'identifiant client est obligatoire")
    @Column(nullable = false, name = "client_id")
    private String clientId;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(nullable = false)
    private Boolean actif = true;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}