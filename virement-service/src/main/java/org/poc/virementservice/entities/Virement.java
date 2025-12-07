package org.poc.virementservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poc.virementservice.enums.StatutVirement;
import org.poc.virementservice.enums.TypeVirement;

import java.time.LocalDateTime;

@Entity
@Table(name = "virements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Virement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long beneficiaireId;

    @Column(nullable = false, length = 24)
    private String ribSource;

    @Column(nullable = false)
    private Double montant;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateVirement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeVirement typeVirement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutVirement statut;

    @PrePersist
    protected void onCreate() {
        if (dateVirement == null) {
            dateVirement = LocalDateTime.now();
        }
        if (statut == null) {
            statut = StatutVirement.EN_ATTENTE;
        }
    }
}