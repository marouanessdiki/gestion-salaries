package com.netcon.gestion_salaries.controller;

import com.netcon.gestion_salaries.records.ParametreDto;
import com.netcon.gestion_salaries.service.inteface.IParametreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parametres")
@RequiredArgsConstructor
public class ParametreController {

    private final IParametreService parametreService;

    @GetMapping("/{type}")
    public ResponseEntity<List<ParametreDto>> getParametreWithType(@PathVariable String type) {
        return ResponseEntity.ok(parametreService.getParametreWithType(type));
    }
}
