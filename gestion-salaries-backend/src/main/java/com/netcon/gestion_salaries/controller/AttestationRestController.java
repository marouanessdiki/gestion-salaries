package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.controller.data.AttestationCmd;
import com.netcon.gestion_salaries.controller.mappers.AttestationMapper;
import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.service.AttestationServiceImpl;
import com.netcon.gestion_salaries.service.EmployeServiceImpl;
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

    private final AttestationServiceImpl attestationService;
    private final AttestationMapper attestationMapper;

    @PostMapping
    public AttestationDto save(@RequestBody AttestationCmd attestationCmd) throws Exception {
        
        AttestationDto attestationDto = attestationMapper.from(attestationCmd);
        attestationService.save(attestationDto);
        return attestationService.generateAndSave(attestationDto);
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

        Path path = Paths.get(attestation.getCheminFichier());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }
}
