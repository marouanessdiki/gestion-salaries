package com.netcon.gestion_salaries.records;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttestationDto {
    private Long id;
    private Long employeId;
    private String typeAttestation; // Travail ou Salaire
    private LocalDateTime dateGeneration;
    private String cheminFichier;
}
