package com.eze.universidad_api.repository;

import com.eze.universidad_api.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    Optional<Asignatura> findByAlumnoIdAndMateriaId(Long alumnoId, Long materiaId);
}