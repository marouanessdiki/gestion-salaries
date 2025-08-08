package com.netcon.gestion_salaries.service.inteface;

import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IEmployeService {
    List<EmployeDto> findAll() ;
    
    EmployeDto save(EmployeDto e) ;
    
    void delete(Long id) ;
    
    Optional<EmployeDto> findById(Long id);
                
}
