package com.eze.universidad_api.service;

import com.eze.universidad_api.dto.MateriaDto;
import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.model.Profesor;
import com.eze.universidad_api.repository.MateriaRepository;
import com.eze.universidad_api.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaService {
    
    @Autowired
    private MateriaRepository materiaRepository;
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    public Optional<Materia> crearMateria(MateriaDto materiaDto) {
        Optional<Profesor> profesorOpt = profesorRepository.findById(materiaDto.getProfesorId());
        if (profesorOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Materia materia = new Materia();
        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getAnio());
        materia.setCuatrimestre(materiaDto.getCuatrimestre());
        materia.setProfesor(profesorOpt.get());
        
        if (materiaDto.getCorrelatividades() != null && !materiaDto.getCorrelatividades().isEmpty()) {
            List<Materia> correlativas = materiaDto.getCorrelatividades().stream()
                .map(id -> materiaRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
            materia.setCorrelatividades(correlativas);
        }
        
        return Optional.of(materiaRepository.save(materia));
    }
    
    public Optional<Materia> buscarPorId(Long id) {
        return materiaRepository.findById(id);
    }
    
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaRepository.findAll();
    }
}