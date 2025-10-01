package com.eze.universidad_api.dto;

import jakarta.validation.constraints.NotBlank;

public class ProfesorDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    
    // Constructores
    public ProfesorDto() {}
    
    public ProfesorDto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}