package com.netcon.gestion_salaries.dao.inteface;

import com.netcon.gestion_salaries.records.EmployeDto;

import java.util.List;

public interface IEmployeDao {
    List<EmployeDto> findAll();
    
    EmployeDto save(EmployeDto e);
    
    void deleteById(Long id);
    
    EmployeDto findById(Long id);
}
