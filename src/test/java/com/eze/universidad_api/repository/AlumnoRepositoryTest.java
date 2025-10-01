package com.eze.universidad_api.repository;

import com.eze.universidad_api.model.Alumno;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AlumnoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Test
    void testExistsByDniCuandoExiste() {
        // Arrange
        Alumno alumno = new Alumno("María", "García", "12345678");
        entityManager.persistAndFlush(alumno);
        
        // Act
        boolean existe = alumnoRepository.existsByDni("12345678");
        
        // Assert
        assertTrue(existe);
    }

    @Test
    void testExistsByDniCuandoNoExiste() {
        // Act
        boolean existe = alumnoRepository.existsByDni("99999999");
        
        // Assert
        assertFalse(existe);
    }

    @Test
    void testExistsByDniConMultiplesAlumnos() {
        // Arrange
        Alumno alumno1 = new Alumno("María", "García", "11111111");
        Alumno alumno2 = new Alumno("Juan", "Pérez", "22222222");
        entityManager.persist(alumno1);
        entityManager.persistAndFlush(alumno2);
        
        // Act & Assert
        assertTrue(alumnoRepository.existsByDni("11111111"));
        assertTrue(alumnoRepository.existsByDni("22222222"));
        assertFalse(alumnoRepository.existsByDni("33333333"));
    }
}