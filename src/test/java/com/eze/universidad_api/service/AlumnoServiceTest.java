package com.eze.universidad_api.service;

import com.eze.universidad_api.dto.AlumnoDto;
import com.eze.universidad_api.dto.EstadoAsignaturaDto;
import com.eze.universidad_api.model.*;
import com.eze.universidad_api.repository.AlumnoRepository;
import com.eze.universidad_api.repository.AsignaturaRepository;
import com.eze.universidad_api.repository.MateriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;
    
    @Mock
    private AsignaturaRepository asignaturaRepository;
    
    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    @Test
    void testCrearAlumnoExitoso() {
        // Arrange
        AlumnoDto alumnoDto = new AlumnoDto("María", "García", "12345678");
        Alumno alumnoMock = new Alumno("María", "García", "12345678");
        alumnoMock.setId(1L);
        
        when(alumnoRepository.existsByDni("12345678")).thenReturn(false);
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoMock);
        when(materiaRepository.findAll()).thenReturn(new ArrayList<>());
        when(asignaturaRepository.saveAll(any())).thenReturn(new ArrayList<>());
        
        // Act
        Optional<Alumno> resultado = alumnoService.crearAlumno(alumnoDto);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("María", resultado.get().getNombre());
        assertEquals("García", resultado.get().getApellido());
        assertEquals("12345678", resultado.get().getDni());
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }

    @Test
    void testCrearAlumnoConDniDuplicado() {
        // Arrange
        AlumnoDto alumnoDto = new AlumnoDto("María", "García", "12345678");
        
        when(alumnoRepository.existsByDni("12345678")).thenReturn(true);
        
        // Act
        Optional<Alumno> resultado = alumnoService.crearAlumno(alumnoDto);
        
        // Assert
        assertFalse(resultado.isPresent());
        verify(alumnoRepository, never()).save(any(Alumno.class));
    }

    @Test
    void testModificarAlumnoExistente() {
        // Arrange
        Long id = 1L;
        AlumnoDto alumnoDto = new AlumnoDto("María Elena", "González", "87654321");
        Alumno alumnoExistente = new Alumno("María", "García", "12345678");
        alumnoExistente.setId(id);
        
        when(alumnoRepository.findById(id)).thenReturn(Optional.of(alumnoExistente));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoExistente);
        
        // Act
        Optional<Alumno> resultado = alumnoService.modificarAlumno(id, alumnoDto);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("María Elena", resultado.get().getNombre());
        assertEquals("González", resultado.get().getApellido());
        assertEquals("87654321", resultado.get().getDni());
    }

    @Test
    void testEliminarAlumnoExistente() {
        // Arrange
        Long id = 1L;
        when(alumnoRepository.existsById(id)).thenReturn(true);
        
        // Act
        boolean resultado = alumnoService.eliminarAlumno(id);
        
        // Assert
        assertTrue(resultado);
        verify(alumnoRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarAlumnoNoExistente() {
        // Arrange
        Long id = 999L;
        when(alumnoRepository.existsById(id)).thenReturn(false);
        
        // Act
        boolean resultado = alumnoService.eliminarAlumno(id);
        
        // Assert
        assertFalse(resultado);
        verify(alumnoRepository, never()).deleteById(any());
    }

    @Test
    void testModificarEstadoAsignatura() {
        // Arrange
        Long alumnoId = 1L;
        Long materiaId = 1L;
        EstadoAsignaturaDto estadoDto = new EstadoAsignaturaDto(EstadoAsignatura.APROBADA);
        estadoDto.setNota(8);
        
        Alumno alumno = new Alumno("Juan", "Pérez", "12345678");
        Materia materia = new Materia("Matemática", 1, 1, new Profesor("Ana", "García"));
        Asignatura asignatura = new Asignatura(alumno, materia, EstadoAsignatura.CURSANDO);
        
        when(asignaturaRepository.findByAlumnoIdAndMateriaId(alumnoId, materiaId))
            .thenReturn(Optional.of(asignatura));
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(asignatura);
        
        // Act
        Optional<Asignatura> resultado = alumnoService.modificarEstadoAsignatura(alumnoId, materiaId, estadoDto);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(EstadoAsignatura.APROBADA, resultado.get().getEstado());
        assertEquals(8, resultado.get().getNota());
    }
}