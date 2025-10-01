package com.eze.universidad_api.service;

import com.eze.universidad_api.dto.MateriaDto;
import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.model.Profesor;
import com.eze.universidad_api.repository.MateriaRepository;
import com.eze.universidad_api.repository.ProfesorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MateriaServiceTest {

    @Mock
    private MateriaRepository materiaRepository;
    
    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private MateriaService materiaService;

    @Test
    void testCrearMateriaExitoso() {
        // Arrange
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matemática I");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setProfesorId(1L);
        materiaDto.setCorrelatividades(Arrays.asList(2L, 3L));
        
        Profesor profesor = new Profesor("Juan", "Pérez");
        profesor.setId(1L);
        
        Materia correlativa1 = new Materia("Algebra", 1, 1, profesor);
        correlativa1.setId(2L);
        Materia correlativa2 = new Materia("Geometría", 1, 2, profesor);
        correlativa2.setId(3L);
        
        Materia materiaMock = new Materia("Matemática I", 1, 1, profesor);
        materiaMock.setId(4L);
        
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));
        when(materiaRepository.findById(2L)).thenReturn(Optional.of(correlativa1));
        when(materiaRepository.findById(3L)).thenReturn(Optional.of(correlativa2));
        when(materiaRepository.save(any(Materia.class))).thenReturn(materiaMock);
        
        // Act
        Optional<Materia> resultado = materiaService.crearMateria(materiaDto);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Matemática I", resultado.get().getNombre());
        assertEquals(1, resultado.get().getAnio());
        assertEquals(1, resultado.get().getCuatrimestre());
        verify(materiaRepository, times(1)).save(any(Materia.class));
    }

    @Test
    void testCrearMateriaProfesorNoExiste() {
        // Arrange
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matemática I");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setProfesorId(999L);
        
        when(profesorRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act
        Optional<Materia> resultado = materiaService.crearMateria(materiaDto);
        
        // Assert
        assertFalse(resultado.isPresent());
        verify(materiaRepository, never()).save(any(Materia.class));
    }

    @Test
    void testBuscarPorId() {
        // Arrange
        Long id = 1L;
        Profesor profesor = new Profesor("Ana", "García");
        Materia materia = new Materia("Física I", 2, 1, profesor);
        materia.setId(id);
        
        when(materiaRepository.findById(id)).thenReturn(Optional.of(materia));
        
        // Act
        Optional<Materia> resultado = materiaService.buscarPorId(id);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Física I", resultado.get().getNombre());
        assertEquals(2, resultado.get().getAnio());
    }
}