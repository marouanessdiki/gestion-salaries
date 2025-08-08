package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.entity.Hr;
import com.netcon.gestion_salaries.repository.HrRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hr")
@CrossOrigin(origins = "http://localhost:3000")
public class HrController {
    private final HrRepository hrRepository;

    public HrController(HrRepository hrRepository) {
        this.hrRepository = hrRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Nom d'utilisateur et mot de passe requis"));
        }
        
        if (hrRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Ce nom d'utilisateur existe déjà"));
        }
        
        Hr hr = new Hr();
        hr.setUsername(username);
        hr.setPassword(password);
        hr.setApproved(false);
        
        hrRepository.save(hr);
        return ResponseEntity.ok(Map.of("success", true, "message", "Inscription réussie. En attente d'approbation par l'administrateur."));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Hr>> getPendingHrs() {
        List<Hr> pendingHrs = hrRepository.findAll().stream()
                .filter(hr -> !hr.isApproved())
                .toList();
        return ResponseEntity.ok(pendingHrs);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveHr(@PathVariable Long id) {
        return hrRepository.findById(id)
                .map(hr -> {
                    hr.setApproved(true);
                    hrRepository.save(hr);
                    return ResponseEntity.ok(Map.of("success", true, "message", "HR approuvé avec succès"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 