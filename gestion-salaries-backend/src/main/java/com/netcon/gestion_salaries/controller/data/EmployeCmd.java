package com.netcon.gestion_salaries.controller.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeCmd {
    private Long id;

    private String nom;
    private String prenom;
    private String cin;
    private String poste;
    private String service;

    private LocalDate dateEmbauche;
}
