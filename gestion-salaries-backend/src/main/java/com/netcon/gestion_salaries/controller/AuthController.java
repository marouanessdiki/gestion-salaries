package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.entity.Hr;
import com.netcon.gestion_salaries.repository.HrRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final HrRepository hrRepository;

    public AuthController(HrRepository hrRepository) {
        this.hrRepository = hrRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        return hrRepository.findByUsername(username)
                .filter(hr -> hr.getPassword().equals(password))
                .map(hr -> {
                    if (!hr.isApproved()) {
                        return ResponseEntity.status(403).body(Map.of("success", false, "message", "Votre compte HR n'a pas encore été approuvé par l'administrateur."));
                    }
                    return ResponseEntity.ok(Map.of("success", true, "role", "hr"));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("success", false, "message", "Identifiants invalides")));
    }
} 