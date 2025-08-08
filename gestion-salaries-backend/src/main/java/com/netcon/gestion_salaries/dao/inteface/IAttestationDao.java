package com.netcon.gestion_salaries.dao.inteface;

import com.netcon.gestion_salaries.records.AttestationDto;

import java.util.List;

public interface IAttestationDao {
    List<AttestationDto> findByEmployeId(Long employeId);
    
    AttestationDto save(AttestationDto attestation);
    
    List<AttestationDto> findAll();
    
    AttestationDto findById(Long id);
}
