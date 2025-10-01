package com.eze.universidad_api.service;

import com.eze.universidad_api.dto.AlumnoDto;
import com.eze.universidad_api.dto.EstadoAsignaturaDto;
import com.eze.universidad_api.model.*;
import com.eze.universidad_api.repository.AlumnoRepository;
import com.eze.universidad_api.repository.AsignaturaRepository;
import com.eze.universidad_api.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlumnoService {
    
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    
    @Autowired
    private MateriaRepository materiaRepository;
    
    public Optional<Alumno> crearAlumno(AlumnoDto alumnoDto) {
        if (alumnoRepository.existsByDni(alumnoDto.getDni())) {
            return Optional.empty();
        }
        
        Alumno alumno = new Alumno();
        alumno.setNombre(alumnoDto.getNombre());
        alumno.setApellido(alumnoDto.getApellido());
        alumno.setDni(alumnoDto.getDni());
        
        Alumno alumnoGuardado = alumnoRepository.save(alumno);
        
        // Crear asignaturas para todas las materias disponibles
        List<Materia> todasLasMaterias = materiaRepository.findAll();
        List<Asignatura> asignaturas = todasLasMaterias.stream()
            .map(materia -> new Asignatura(alumnoGuardado, materia, EstadoAsignatura.NO_CURSADA))
            .collect(Collectors.toList());
        
        asignaturaRepository.saveAll(asignaturas);
        
        return Optional.of(alumnoGuardado);
    }
    
    public Optional<Alumno> modificarAlumno(Long id, AlumnoDto alumnoDto) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);
        if (alumnoOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            alumno.setNombre(alumnoDto.getNombre());
            alumno.setApellido(alumnoDto.getApellido());
            alumno.setDni(alumnoDto.getDni());
            return Optional.of(alumnoRepository.save(alumno));
        }
        return Optional.empty();
    }
    
    public boolean eliminarAlumno(Long id) {
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Optional<Asignatura> modificarEstadoAsignatura(Long alumnoId, Long asignaturaId, EstadoAsignaturaDto estadoDto) {
        Optional<Asignatura> asignaturaOpt = asignaturaRepository.findByAlumnoIdAndMateriaId(alumnoId, asignaturaId);
        if (asignaturaOpt.isPresent()) {
            Asignatura asignatura = asignaturaOpt.get();
            asignatura.setEstado(estadoDto.getEstado());
            if (estadoDto.getNota() != null) {
                asignatura.setNota(estadoDto.getNota());
            }
            return Optional.of(asignaturaRepository.save(asignatura));
        }
        return Optional.empty();
    }
    
    public Optional<Alumno> buscarPorId(Long id) {
        return alumnoRepository.findById(id);
    }
}
