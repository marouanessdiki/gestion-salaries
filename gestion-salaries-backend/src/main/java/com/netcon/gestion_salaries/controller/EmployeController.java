package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.service.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {
    private final EmployeService employeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employes", employeService.findAll());
        return "employes/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employe", new Employe());
        return "employes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employe employe) {
        employeService.save(employe);
        return "redirect:/employes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        employeService.findById(id).ifPresent(e -> model.addAttribute("employe", e));
        return "employes/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeService.delete(id);
        return "redirect:/employes";
    }
}
