package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.service.AttestationService;
import com.netcon.gestion_salaries.service.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attestations")
@RequiredArgsConstructor
public class AttestationController {
    private final AttestationService attestationService;
    private final EmployeService employeService;

    @GetMapping("/generate/{employeId}")
    public String generateForm(@PathVariable Long employeId, Model model) {
        Employe employe = employeService.findById(employeId).orElseThrow();
        model.addAttribute("employe", employe);
        model.addAttribute("attestation", new Attestation());
        return "attestations/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Attestation attestation) {
        attestationService.save(attestation);
        return "redirect:/employes";
    }

    @GetMapping("/history/{employeId}")
    public String history(@PathVariable Long employeId, Model model) {
        model.addAttribute("attestations", attestationService.findByEmploye(employeId));
        return "attestations/history";
    }
}
