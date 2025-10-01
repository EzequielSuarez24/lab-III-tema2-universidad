package com.eze.universidad_api.controller;

import com.eze.universidad_api.model.Alumno;
import com.eze.universidad_api.model.Asignatura;
import com.eze.universidad_api.service.AlumnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlumnoController.class)
class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AlumnoService alumnoService;

    @Test
    void testCrearAlumno() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        
        when(alumnoService.crearAlumno(any())).thenReturn(Optional.of(alumno));
        
        mockMvc.perform(post("/alumno")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"María\",\"apellido\":\"García\",\"dni\":\"12345678\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testModificarAlumno() throws Exception {
        Alumno alumno = new Alumno();
        
        when(alumnoService.modificarAlumno(any(), any())).thenReturn(Optional.of(alumno));
        
        mockMvc.perform(put("/alumno/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"María\",\"apellido\":\"García\",\"dni\":\"12345678\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarAlumno() throws Exception {
        when(alumnoService.eliminarAlumno(1L)).thenReturn(true);
        
        mockMvc.perform(delete("/alumno/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testModificarEstadoAsignatura() throws Exception {
        Asignatura asignatura = new Asignatura();
        
        when(alumnoService.modificarEstadoAsignatura(any(), any(), any())).thenReturn(Optional.of(asignatura));
        
        mockMvc.perform(put("/alumno/1/asignatura/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\":\"CURSANDO\"}"))
                .andExpect(status().isOk());
    }
}