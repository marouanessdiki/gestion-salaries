package com.netcon.gestion_salaries.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "attestation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attestation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "type_attestation")
    private String typeAttestation; // Travail ou Salaire
    
    @Column(name = "date_generation")
    private LocalDateTime dateGeneration;

    @Column(name = "chemin_fichier")
    private String cheminFichier;
}
