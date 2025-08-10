package com.netcon.gestion_salaries.dao;

import com.netcon.gestion_salaries.dao.inteface.IEmployeDao;
import com.netcon.gestion_salaries.dao.mappers.EmployeMapper;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class EmployeeDaoImpl implements IEmployeDao {
    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;

    @Override
    public List<EmployeDto> findAll() {
        return employeMapper.fromList(employeRepository.findAll());
    }

    @Override
    public EmployeDto save(EmployeDto e) {
        return employeMapper.from(employeRepository.save(employeMapper.from(e)));
    }

    @Override
    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }

    @Override
    public EmployeDto findById(Long id) {
        return employeMapper.from(employeRepository.findById(id).orElse(null));
    }
}
