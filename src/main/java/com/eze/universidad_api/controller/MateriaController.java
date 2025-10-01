package com.eze.universidad_api.controller;

import com.eze.universidad_api.dto.MateriaDto;
import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.service.MateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/materia")
public class MateriaController {
    
    @Autowired
    private MateriaService materiaService;
    
    @PostMapping
    public ResponseEntity<Materia> crearMateria(@Valid @RequestBody MateriaDto materiaDto) {
        try {
            Optional<Materia> materia = materiaService.crearMateria(materiaDto);
            if (materia.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(materia.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

