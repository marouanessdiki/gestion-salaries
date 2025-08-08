package com.netcon.gestion_salaries.controller.data;

import com.netcon.gestion_salaries.entity.Employe;

import java.time.LocalDateTime;

public class AttestationCmd {
    private Long id;
    private Long employeId;
    private String typeAttestation;
    private LocalDateTime dateGeneration;
    private String cheminFichier;
}
