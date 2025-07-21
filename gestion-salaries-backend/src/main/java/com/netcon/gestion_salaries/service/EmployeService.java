package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeService {
    private final EmployeRepository employeRepository;

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Employe save(Employe e) {
        return employeRepository.save(e);
    }

    public void delete(Long id) {
        employeRepository.deleteById(id);
    }

    public Optional<Employe> findById(Long id) {
        return employeRepository.findById(id);
    }
}
