package com.eze.universidad_api.controller;

import com.eze.universidad_api.dto.ProfesorDto;
import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.model.Profesor;
import com.eze.universidad_api.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;
    
    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@Valid @RequestBody ProfesorDto profesorDto) {
        try {
            Profesor profesor = profesorService.crearProfesor(profesorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(profesor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{idProfesor}")
    public ResponseEntity<Profesor> modificarProfesor(@PathVariable Long idProfesor, @Valid @RequestBody ProfesorDto profesorDto) {
        try {
            Optional<Profesor> profesor = profesorService.modificarProfesor(idProfesor, profesorDto);
            if (profesor.isPresent()) {
                return ResponseEntity.ok(profesor.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{idProfesor}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long idProfesor) {
        try {
            boolean eliminado = profesorService.eliminarProfesor(idProfesor);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{idProfesor}/materias")
    public ResponseEntity<List<Materia>> obtenerMateriasPorProfesor(@PathVariable Long idProfesor) {
        try {
            Optional<Profesor> profesor = profesorService.buscarPorId(idProfesor);
            if (profesor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            List<Materia> materias = profesorService.obtenerMateriasPorProfesor(idProfesor);
            if (materias.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(materias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodosLosProfesores() {
        try {
            List<Profesor> profesores = profesorService.obtenerTodos();
            if (profesores.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
                return ResponseEntity.ok(profesores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}