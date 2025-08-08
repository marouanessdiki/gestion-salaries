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

    private EmployeDto employe;

    private String typeAttestation; // Travail ou Salaire
    private LocalDateTime dateGeneration;

    private String cheminFichier;
    private Long employeeId;
}
