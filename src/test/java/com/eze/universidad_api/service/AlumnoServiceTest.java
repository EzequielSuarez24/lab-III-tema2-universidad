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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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

        AlumnoDto alumnoDto = new AlumnoDto("María", "García", "12345678");
        Alumno alumnoMock = new Alumno("María", "García", "12345678");
        alumnoMock.setId(1L);
        
        List<Materia> materias = Arrays.asList(
            new Materia("Matemática I", 1, 1, new Profesor())
        );
        
        when(alumnoRepository.existsByDni("12345678")).thenReturn(false);
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoMock);
        when(materiaRepository.findAll()).thenReturn(materias);
        when(asignaturaRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        Optional<Alumno> resultado = alumnoService.crearAlumno(alumnoDto);
        

        assertTrue(resultado.isPresent());
        assertEquals("María", resultado.get().getNombre());
        verify(asignaturaRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCrearAlumnoConDniDuplicado() {

        AlumnoDto alumnoDto = new AlumnoDto("María", "García", "12345678");
        
        when(alumnoRepository.existsByDni("12345678")).thenReturn(true);
        

        Optional<Alumno> resultado = alumnoService.crearAlumno(alumnoDto);

        assertFalse(resultado.isPresent());
        verify(alumnoRepository, never()).save(any(Alumno.class));
    }

    @Test
    void testModificarAlumnoExistente() {

        Long id = 1L;
        AlumnoDto alumnoDto = new AlumnoDto("María Elena", "García López", "87654321");
        Alumno alumnoExistente = new Alumno("María", "García", "12345678");
        alumnoExistente.setId(id);
        
        when(alumnoRepository.findById(id)).thenReturn(Optional.of(alumnoExistente));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoExistente);
        

        Optional<Alumno> resultado = alumnoService.modificarAlumno(id, alumnoDto);
        

        assertTrue(resultado.isPresent());
        assertEquals("María Elena", resultado.get().getNombre());
    }

    @Test
    void testModificarAlumnoNoExistente() {

        Long id = 999L;
        AlumnoDto alumnoDto = new AlumnoDto("No", "Existe", "99999999");
        
        when(alumnoRepository.findById(id)).thenReturn(Optional.empty());
        

        Optional<Alumno> resultado = alumnoService.modificarAlumno(id, alumnoDto);
        

        assertFalse(resultado.isPresent());
        verify(alumnoRepository, never()).save(any());
    }

    @Test
    void testEliminarAlumnoExistente() {

        Long id = 1L;
        when(alumnoRepository.existsById(id)).thenReturn(true);
        

        boolean resultado = alumnoService.eliminarAlumno(id);
        

        assertTrue(resultado);
        verify(alumnoRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarAlumnoNoExistente() {

        Long id = 999L;
        when(alumnoRepository.existsById(id)).thenReturn(false);
        

        boolean resultado = alumnoService.eliminarAlumno(id);
        

        assertFalse(resultado);
        verify(alumnoRepository, never()).deleteById(any());
    }

    @Test
    void testModificarEstadoAsignaturaExitoso() {

        Long alumnoId = 1L;
        Long materiaId = 1L;
        EstadoAsignaturaDto estadoDto = new EstadoAsignaturaDto(EstadoAsignatura.APROBADA);
        estadoDto.setNota(8);
        
        Alumno alumno = new Alumno("Juan", "Pérez", "12345678");
        Materia materia = new Materia("Matemática", 1, 1, new Profesor());
        Asignatura asignatura = new Asignatura(alumno, materia, EstadoAsignatura.CURSANDO);
        
        when(asignaturaRepository.findByAlumnoIdAndMateriaId(alumnoId, materiaId))
            .thenReturn(Optional.of(asignatura));
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(asignatura);
        

        Optional<Asignatura> resultado = alumnoService.modificarEstadoAsignatura(alumnoId, materiaId, estadoDto);
        

        assertTrue(resultado.isPresent());
        assertEquals(EstadoAsignatura.APROBADA, resultado.get().getEstado());
        assertEquals(8, resultado.get().getNota());
    }

    @Test
    void testModificarEstadoAsignaturaNoExistente() {

        when(asignaturaRepository.findByAlumnoIdAndMateriaId(999L, 1L))
            .thenReturn(Optional.empty());
        
        Optional<Asignatura> resultado = alumnoService.modificarEstadoAsignatura(
            999L, 1L, new EstadoAsignaturaDto(EstadoAsignatura.CURSANDO));
        

        assertFalse(resultado.isPresent());
        verify(asignaturaRepository, never()).save(any());
    }

    @Test
    void testBuscarPorIdExistente() {

        Long id = 1L;
        Alumno alumno = new Alumno("Lucía", "Rodríguez", "11223344");
        alumno.setId(id);
        
        when(alumnoRepository.findById(id)).thenReturn(Optional.of(alumno));
        

        Optional<Alumno> resultado = alumnoService.buscarPorId(id);
        

        assertTrue(resultado.isPresent());
        assertEquals("Lucía", resultado.get().getNombre());
    }

    @Test
    void testBuscarPorIdNoExistente() {

        when(alumnoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Alumno> resultado = alumnoService.buscarPorId(999L);
        

        assertFalse(resultado.isPresent());
    }
}