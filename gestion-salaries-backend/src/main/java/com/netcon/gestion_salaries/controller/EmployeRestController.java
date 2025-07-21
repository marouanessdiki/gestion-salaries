package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.service.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeRestController {
    private final EmployeService employeService;

    @GetMapping
    public List<Employe> getAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public Employe getOne(@PathVariable Long id) {
        return employeService.findById(id).orElseThrow();
    }

    @PostMapping
    public Employe save(@RequestBody Employe employe) {
        return employeService.save(employe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeService.delete(id);
    }
}
