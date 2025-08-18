package com.netcon.gestion_salaries.repository;

import com.netcon.gestion_salaries.entity.Attestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AttestationRepository extends JpaRepository<Attestation, Long> {
    @Query("SELECT a FROM Attestation a WHERE a.employeId = :employeId")
    List<Attestation> findByEmployeId(@Param("employeId") Long employeId);

    @Modifying
    @Transactional
    @Query("UPDATE Attestation a SET a.cheminFichier = :path WHERE a.id = :id")
    int updateCheminFichier(@Param("id") Long id, @Param("path") String path);
}
