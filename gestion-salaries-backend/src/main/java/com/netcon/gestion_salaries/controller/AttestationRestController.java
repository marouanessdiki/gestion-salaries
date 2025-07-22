package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.service.AttestationService;
import com.netcon.gestion_salaries.service.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/attestations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AttestationRestController {

    private final AttestationService attestationService;
    private final EmployeService employeService;

    @PostMapping
    public Attestation save(@RequestBody Attestation attestation) throws Exception {
        Employe employe = employeService.findById(attestation.getEmploye().getId())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        attestation.setEmploye(employe);

        return attestationService.generateAndSave(attestation);
    }

    @GetMapping
    public List<Attestation> findAll() {
        return attestationService.findAll();
    }

    @GetMapping("/employe/{id}")
    public List<Attestation> findByEmploye(@PathVariable Long id) {
        return attestationService.findByEmploye(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws Exception {
        Attestation attestation = attestationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Attestation introuvable"));

        Path path = Paths.get(attestation.getCheminFichier());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }
}
