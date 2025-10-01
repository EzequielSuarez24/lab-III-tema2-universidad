package com.eze.universidad_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class MateriaDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotNull(message = "El año es obligatorio")
    @Positive(message = "El año debe ser positivo")
    private Integer anio;
    
    @NotNull(message = "El cuatrimestre es obligatorio")
    @Positive(message = "El cuatrimestre debe ser positivo")
    private Integer cuatrimestre;
    
    @NotNull(message = "El ID del profesor es obligatorio")
    private Long profesorId;
    
    private List<Long> correlatividades;
    
    // Constructores
    public MateriaDto() {}
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    
    public Integer getCuatrimestre() { return cuatrimestre; }
    public void setCuatrimestre(Integer cuatrimestre) { this.cuatrimestre = cuatrimestre; }
    
    public Long getProfesorId() { return profesorId; }
    public void setProfesorId(Long profesorId) { this.profesorId = profesorId; }
    
    public List<Long> getCorrelatividades() { return correlatividades; }
    public void setCorrelatividades(List<Long> correlatividades) { this.correlatividades = correlatividades; }
}