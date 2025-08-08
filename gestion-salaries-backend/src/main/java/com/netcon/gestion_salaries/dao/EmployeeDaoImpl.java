package com.netcon.gestion_salaries.dao;

import com.netcon.gestion_salaries.dao.inteface.IAttestationDao;
import com.netcon.gestion_salaries.dao.inteface.IEmployeDao;
import com.netcon.gestion_salaries.dao.mappers.AttestationMapper;
import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.exceptions.EmployeException;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.repository.AttestationRepository;
import com.netcon.gestion_salaries.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
//TODO
public class EmployeeDaoImpl implements IEmployeDao {
    
    @Override
    public List<EmployeDto> findAll() {
        return null;
    }
    
    @Override
    public EmployeDto save(EmployeDto e) {
        return null;
    }
    
    @Override
    public void deleteById(Long id) {
    
    }
    
    @Override
    public Optional<EmployeDto> findById(Long id) {
        return Optional.empty();
    }
}
