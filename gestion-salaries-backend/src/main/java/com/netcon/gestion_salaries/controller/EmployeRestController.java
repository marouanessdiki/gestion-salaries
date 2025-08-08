package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.controller.data.EmployeCmd;
import com.netcon.gestion_salaries.controller.mappers.EmployeMapper;
import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.service.EmployeServiceImpl;
import com.netcon.gestion_salaries.service.inteface.IEmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeRestController {
    private final IEmployeService employeService;
    private final EmployeMapper employeMapper;

    @GetMapping
    public List<EmployeDto> getAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeDto getOne(@PathVariable Long id) {
        return employeService.findById(id).orElseThrow();
    }

    @PostMapping
    public EmployeDto save(@RequestBody EmployeCmd employe) {
        employeMapper.from(employe);
        return employeService.save(employeMapper.from(employe));
    }

    @PutMapping("/{id}")
    public EmployeDto update(@PathVariable Long id, @RequestBody EmployeCmd employeCmd) {
        EmployeDto employe = employeMapper.from(employeCmd);
        employe.setId(id);
        return employeService.save(employe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeService.delete(id);
    }
}
