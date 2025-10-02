package com.eze.universidad_api.repository;

import com.eze.universidad_api.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AsignaturaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Test
    void testFindByAlumnoIdAndMateriaIdCuandoExiste() {
       
        Profesor profesor = new Profesor("Juan", "Pérez");
        entityManager.persist(profesor);
        
        Alumno alumno = new Alumno("María", "García", "12345678");
        entityManager.persist(alumno);
        
        Materia materia = new Materia("Matemática I", 1, 1, profesor);
        entityManager.persist(materia);
        
        Asignatura asignatura = new Asignatura(alumno, materia, EstadoAsignatura.NO_CURSADA);
        entityManager.persistAndFlush(asignatura);
        

        Optional<Asignatura> resultado = asignaturaRepository.findByAlumnoIdAndMateriaId(
            alumno.getId(), materia.getId());
        
  
        assertTrue(resultado.isPresent());
        assertEquals(EstadoAsignatura.NO_CURSADA, resultado.get().getEstado());
    }

    @Test
    void testFindByAlumnoIdAndMateriaIdCuandoNoExiste() {
        Optional<Asignatura> resultado = asignaturaRepository.findByAlumnoIdAndMateriaId(999L, 999L);
        
        assertFalse(resultado.isPresent());
    }

    @Test
    void testFindByAlumnoIdAndMateriaIdConMultiplesAsignaturas() {
        // Arrange
        Profesor profesor = new Profesor("Ana", "García");
        entityManager.persist(profesor);
        
        Alumno alumno1 = new Alumno("María", "López", "11111111");
        Alumno alumno2 = new Alumno("Carlos", "Pérez", "22222222");
        entityManager.persist(alumno1);
        entityManager.persist(alumno2);
        
        Materia materia1 = new Materia("Matemática", 1, 1, profesor);
        Materia materia2 = new Materia("Física", 1, 1, profesor);
        entityManager.persist(materia1);
        entityManager.persist(materia2);
        
        Asignatura asig1 = new Asignatura(alumno1, materia1, EstadoAsignatura.CURSANDO);
        Asignatura asig2 = new Asignatura(alumno2, materia2, EstadoAsignatura.APROBADA);
        entityManager.persist(asig1);
        entityManager.persistAndFlush(asig2);
        
        // Act & Assert
        Optional<Asignatura> resultado1 = asignaturaRepository.findByAlumnoIdAndMateriaId(
            alumno1.getId(), materia1.getId());
        Optional<Asignatura> resultado2 = asignaturaRepository.findByAlumnoIdAndMateriaId(
            alumno2.getId(), materia2.getId());
        
        assertTrue(resultado1.isPresent());
        assertTrue(resultado2.isPresent());
        assertEquals(EstadoAsignatura.CURSANDO, resultado1.get().getEstado());
        assertEquals(EstadoAsignatura.APROBADA, resultado2.get().getEstado());
    }
}