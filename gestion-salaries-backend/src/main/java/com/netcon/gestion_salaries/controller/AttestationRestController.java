package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.controller.data.AttestationCmd;
import com.netcon.gestion_salaries.controller.mappers.AttestationCmdMapper;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.service.inteface.IAttestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/attestations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AttestationRestController {

    private final IAttestationService attestationService;
    private final AttestationCmdMapper attestationMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AttestationCmd attestationCmd) {
        try {
            // Validate required fields
            if (attestationCmd.getEmployeId() == null) {
                return ResponseEntity.badRequest()
                        .body("Error: employeId is required");
            }
            if (attestationCmd.getTypeAttestation() == null || attestationCmd.getTypeAttestation().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Error: typeAttestation is required");
            }

            AttestationDto attestationDto = attestationMapper.from(attestationCmd);
            // Only generate and save once to avoid duplicates
            AttestationDto result = attestationService.generateAndSave(attestationDto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // For debugging in logs
            return ResponseEntity.status(500)
                    .body("Error generating attestation: " + e.getMessage());
        }
    }

    @GetMapping
    public List<AttestationDto> findAll() {
        return attestationService.findAll();
    }

    @GetMapping("/employe/{id}")
    public List<AttestationDto> findByEmploye(@PathVariable Long id) {
        return attestationService.findByEmploye(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws Exception {
        AttestationDto attestation = attestationService.findById(id);

        String chemin = attestation.getCheminFichier();
        if (chemin == null || chemin.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        // Normalize legacy values like "/attestations/attestation_1.pdf" -> "pdfs/attestation_1.pdf"
        String normalized = chemin.replace('\\', '/');
        if (normalized.startsWith("/attestations/")) {
            String fileName = java.nio.file.Paths.get(normalized).getFileName().toString();
            normalized = "pdfs/" + fileName;
        }

        java.nio.file.Path path = java.nio.file.Paths.get(normalized);

        // Fallback: if the path still doesn't exist, try putting the filename under pdfs/
        if (!java.nio.file.Files.exists(path) || !java.nio.file.Files.isReadable(path)) {
            String fileName = java.nio.file.Paths.get(chemin).getFileName().toString();
            java.nio.file.Path alt = java.nio.file.Paths.get("pdfs", fileName);
            if (java.nio.file.Files.exists(alt) && java.nio.file.Files.isReadable(alt)) {
                path = alt;
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        attestationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
