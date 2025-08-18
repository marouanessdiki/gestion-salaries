package com.netcon.gestion_salaries.controller.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttestationCmd {
    private Long id;
    private Long employeId;
    private String typeAttestation;
    private LocalDateTime dateGeneration;
    private String cheminFichier;
}
