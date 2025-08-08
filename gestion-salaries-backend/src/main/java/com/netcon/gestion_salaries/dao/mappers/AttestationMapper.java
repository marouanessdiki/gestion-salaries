package com.netcon.gestion_salaries.dao.mappers;

import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AttestationMapper {
    
    Attestation from(AttestationDto dto);
    AttestationDto from(Attestation entity);
    List<AttestationDto> fromList(List<Attestation> entity);
}
