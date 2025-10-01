package com.eze.universidad_api.controller;

import com.eze.universidad_api.dto.AlumnoDto;
import com.eze.universidad_api.dto.EstadoAsignaturaDto;
import com.eze.universidad_api.model.Alumno;
import com.eze.universidad_api.model.Asignatura;
import com.eze.universidad_api.service.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    
    @Autowired
    private AlumnoService alumnoService;
    
    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@Valid @RequestBody AlumnoDto alumnoDto) {
        try {
            Optional<Alumno> alumno = alumnoService.crearAlumno(alumnoDto);
            if (alumno.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(alumno.get());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{idAlumno}")
    public ResponseEntity<Alumno> modificarAlumno(@PathVariable Long idAlumno, @Valid @RequestBody AlumnoDto alumnoDto) {
        try {
            Optional<Alumno> alumno = alumnoService.modificarAlumno(idAlumno, alumnoDto);
            if (alumno.isPresent()) {
                return ResponseEntity.ok(alumno.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{idAlumno}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long idAlumno) {
        try {
            boolean eliminado = alumnoService.eliminarAlumno(idAlumno);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public ResponseEntity<Asignatura> modificarEstadoAsignatura(
        @PathVariable Long idAlumno, 
        @PathVariable Long idAsignatura, 
        @Valid @RequestBody EstadoAsignaturaDto estadoDto) {
        try {
            Optional<Asignatura> asignatura = alumnoService.modificarEstadoAsignatura(idAlumno, idAsignatura, estadoDto);
            if (asignatura.isPresent()) {
                return ResponseEntity.ok(asignatura.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}