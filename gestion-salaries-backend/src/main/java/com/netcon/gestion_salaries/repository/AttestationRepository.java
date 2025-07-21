package com.netcon.gestion_salaries.repository;

import com.netcon.gestion_salaries.entity.Attestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttestationRepository extends JpaRepository<Attestation, Long> {
    List<Attestation> findByEmployeId(Long employeId);
}
