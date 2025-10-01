package com.eze.universidad_api.dto;

import com.eze.universidad_api.model.EstadoAsignatura;
import jakarta.validation.constraints.NotNull;

public class EstadoAsignaturaDto {
    @NotNull(message = "El estado es obligatorio")
    private EstadoAsignatura estado;
    
    private Integer nota;
    
    // Constructores
    public EstadoAsignaturaDto() {}
    
    public EstadoAsignaturaDto(EstadoAsignatura estado) {
        this.estado = estado;
    }
    
    // Getters y Setters
    public EstadoAsignatura getEstado() { return estado; }
    public void setEstado(EstadoAsignatura estado) { this.estado = estado; }
    
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
}