package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.repository.AttestationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttestationService {
    private final AttestationRepository attestationRepository;

    public List<Attestation> findByEmploye(Long employeId) {
        return attestationRepository.findByEmployeId(employeId);
    }

    public Attestation save(Attestation attestation) {
        attestation.setDateGeneration(LocalDateTime.now());
        return attestationRepository.save(attestation);
    }
}
