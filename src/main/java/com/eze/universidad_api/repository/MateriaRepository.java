package com.eze.universidad_api.repository;

import com.eze.universidad_api.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    @Query("SELECT m FROM Materia m WHERE m.profesor.id = :profesorId ORDER BY m.nombre ASC")
    List<Materia> findByProfesorIdOrderByNombreAsc(@Param("profesorId") Long profesorId);
}