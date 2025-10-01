package com.eze.universidad_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private Integer anio;
    
    @Column(nullable = false)
    private Integer cuatrimestre;
    
    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)
    @JsonBackReference  // <-- AGREGAR ESTA LÍNEA
    private Profesor profesor;
    
    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;
    
    @ManyToMany
    @JoinTable(
        name = "materia_correlativas",
        joinColumns = @JoinColumn(name = "materia_id"),
        inverseJoinColumns = @JoinColumn(name = "correlativa_id")
    )
    private List<Materia> correlatividades;
    
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asignatura> asignaturas;
    
    // Constructores
    public Materia() {}
    
    public Materia(String nombre, Integer anio, Integer cuatrimestre, Profesor profesor) {
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
    }
    
    // Getters y Setters (mantener todos iguales)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    
    public Integer getCuatrimestre() { return cuatrimestre; }
    public void setCuatrimestre(Integer cuatrimestre) { this.cuatrimestre = cuatrimestre; }
    
    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }
    
    public Carrera getCarrera() { return carrera; }
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }
    
    public List<Materia> getCorrelatividades() { return correlatividades; }
    public void setCorrelatividades(List<Materia> correlatividades) { this.correlatividades = correlatividades; }
    
    public List<Asignatura> getAsignaturas() { return asignaturas; }
    public void setAsignaturas(List<Asignatura> asignaturas) { this.asignaturas = asignaturas; }
}