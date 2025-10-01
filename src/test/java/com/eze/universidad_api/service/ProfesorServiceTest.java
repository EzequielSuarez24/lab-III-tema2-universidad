package com.eze.universidad_api.service;

import com.eze.universidad_api.dto.ProfesorDto;
import com.eze.universidad_api.model.Profesor;
import com.eze.universidad_api.repository.MateriaRepository;
import com.eze.universidad_api.repository.ProfesorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private ProfesorService profesorService;

    @Test
    void testCrearProfesor() {
        // Arrange
        ProfesorDto profesorDto = new ProfesorDto("Juan", "Pérez");
        Profesor profesorMock = new Profesor("Juan", "Pérez");
        profesorMock.setId(1L);
        
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesorMock);
        
        // Act
        Profesor resultado = profesorService.crearProfesor(profesorDto);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        verify(profesorRepository, times(1)).save(any(Profesor.class));
    }

    @Test
    void testModificarProfesorExistente() {
        // Arrange
        Long id = 1L;
        ProfesorDto profesorDto = new ProfesorDto("Juan Carlos", "González");
        Profesor profesorExistente = new Profesor("Juan", "Pérez");
        profesorExistente.setId(id);
        
        when(profesorRepository.findById(id)).thenReturn(Optional.of(profesorExistente));
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesorExistente);
        
        // Act
        Optional<Profesor> resultado = profesorService.modificarProfesor(id, profesorDto);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Juan Carlos", resultado.get().getNombre());
        assertEquals("González", resultado.get().getApellido());
    }

    @Test
    void testModificarProfesorNoExistente() {
        // Arrange
        Long id = 999L;
        ProfesorDto profesorDto = new ProfesorDto("Juan", "Pérez");
        
        when(profesorRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act
        Optional<Profesor> resultado = profesorService.modificarProfesor(id, profesorDto);
        
        // Assert
        assertFalse(resultado.isPresent());
        verify(profesorRepository, never()).save(any(Profesor.class));
    }

    @Test
    void testEliminarProfesorExistente() {
        // Arrange
        Long id = 1L;
        when(profesorRepository.existsById(id)).thenReturn(true);
        
        // Act
        boolean resultado = profesorService.eliminarProfesor(id);
        
        // Assert
        assertTrue(resultado);
        verify(profesorRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarProfesorNoExistente() {
        // Arrange
        Long id = 999L;
        when(profesorRepository.existsById(id)).thenReturn(false);
        
        // Act
        boolean resultado = profesorService.eliminarProfesor(id);
        
        // Assert
        assertFalse(resultado);
        verify(profesorRepository, never()).deleteById(any());
    }

    @Test
    void testBuscarPorId() {
        // Arrange
        Long id = 1L;
        Profesor profesor = new Profesor("Ana", "García");
        profesor.setId(id);
        
        when(profesorRepository.findById(id)).thenReturn(Optional.of(profesor));
        
        // Act
        Optional<Profesor> resultado = profesorService.buscarPorId(id);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
        assertEquals("García", resultado.get().getApellido());
    }
}