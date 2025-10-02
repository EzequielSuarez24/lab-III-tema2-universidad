package com.eze.universidad_api.repository;

import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.model.Profesor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MateriaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private MateriaRepository materiaRepository;

    @Test
    void testFindByProfesorIdOrderByNombreAscConMaterias() {
        // Arrange
        Profesor profesor = new Profesor("Juan", "Pérez");
        entityManager.persist(profesor);
        
        Materia materia1 = new Materia("Zebra Materia", 1, 1, profesor);
        Materia materia2 = new Materia("Alpha Materia", 1, 2, profesor);
        Materia materia3 = new Materia("Beta Materia", 2, 1, profesor);
        
        entityManager.persist(materia1);
        entityManager.persist(materia2);
        entityManager.persist(materia3);
        entityManager.flush();
        
        // Act
        List<Materia> resultado = materiaRepository.findByProfesorIdOrderByNombreAsc(profesor.getId());
        
        // Assert
        assertEquals(3, resultado.size());
        assertEquals("Alpha Materia", resultado.get(0).getNombre());
        assertEquals("Beta Materia", resultado.get(1).getNombre());
        assertEquals("Zebra Materia", resultado.get(2).getNombre());
    }

    @Test
    void testFindByProfesorIdOrderByNombreAscSinMaterias() {
        // Arrange
        Profesor profesor = new Profesor("Sin", "Materias");
        entityManager.persistAndFlush(profesor);
        
        
        List<Materia> resultado = materiaRepository.findByProfesorIdOrderByNombreAsc(profesor.getId());
        

        assertTrue(resultado.isEmpty());
    }

    @Test
    void testFindByProfesorIdOrderByNombreAscProfesorInexistente() {
        
        List<Materia> resultado = materiaRepository.findByProfesorIdOrderByNombreAsc(999L);
        

        assertTrue(resultado.isEmpty());
    }
}