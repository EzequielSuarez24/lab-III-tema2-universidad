package com.eze.universidad_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;
    
    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAsignatura estado;
    
    private Integer nota;
    
    // Constructores
    public Asignatura() {}
    
    public Asignatura(Alumno alumno, Materia materia, EstadoAsignatura estado) {
        this.alumno = alumno;
        this.materia = materia;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
    
    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }
    
    public EstadoAsignatura getEstado() { return estado; }
    public void setEstado(EstadoAsignatura estado) { this.estado = estado; }
    
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
}