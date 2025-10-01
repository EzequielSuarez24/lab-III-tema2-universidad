package com.eze.universidad_api.service;

import com.eze.universidad_api.dto.ProfesorDto;
import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.model.Profesor;
import com.eze.universidad_api.repository.MateriaRepository;
import com.eze.universidad_api.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    @Autowired
    private MateriaRepository materiaRepository;
    
    public Profesor crearProfesor(ProfesorDto profesorDto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(profesorDto.getNombre());
        profesor.setApellido(profesorDto.getApellido());
        return profesorRepository.save(profesor);
    }
    
    public Optional<Profesor> modificarProfesor(Long id, ProfesorDto profesorDto) {
        Optional<Profesor> profesorOpt = profesorRepository.findById(id);
        if (profesorOpt.isPresent()) {
            Profesor profesor = profesorOpt.get();
            profesor.setNombre(profesorDto.getNombre());
            profesor.setApellido(profesorDto.getApellido());
            return Optional.of(profesorRepository.save(profesor));
        }
        return Optional.empty();
    }
    
    public boolean eliminarProfesor(Long id) {
        if (profesorRepository.existsById(id)) {
            profesorRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Materia> obtenerMateriasPorProfesor(Long profesorId) {
        return materiaRepository.findByProfesorIdOrderByNombreAsc(profesorId);
    }
    
    public Optional<Profesor> buscarPorId(Long id) {
        return profesorRepository.findById(id);
    }
    
    // AGREGAR ESTE MÉTODO:
    public List<Profesor> obtenerTodos() {
        return profesorRepository.findAll();
    }
}
