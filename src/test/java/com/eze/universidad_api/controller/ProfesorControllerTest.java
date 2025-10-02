package com.eze.universidad_api.controller;

import com.eze.universidad_api.dto.ProfesorDto;
import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.model.Profesor;
import com.eze.universidad_api.service.ProfesorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfesorController.class)
class ProfesorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProfesorService profesorService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearProfesorExitoso() throws Exception {
        
        ProfesorDto dto = new ProfesorDto("Juan", "Pérez");
        Profesor profesor = new Profesor("Juan", "Pérez");
        profesor.setId(1L);
        
        when(profesorService.crearProfesor(any(ProfesorDto.class))).thenReturn(profesor);
        
        
        mockMvc.perform(post("/profesor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"));
    }

    @Test
    void testCrearProfesorConError() throws Exception {
        
        when(profesorService.crearProfesor(any())).thenThrow(new RuntimeException("Error"));
        

        mockMvc.perform(post("/profesor")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan\",\"apellido\":\"Pérez\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModificarProfesorExitoso() throws Exception {
   
        Profesor profesor = new Profesor("Juan Carlos", "Pérez");
        profesor.setId(1L);
        
        when(profesorService.modificarProfesor(eq(1L), any(ProfesorDto.class)))
            .thenReturn(Optional.of(profesor));

        mockMvc.perform(put("/profesor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan Carlos\",\"apellido\":\"Pérez\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Carlos"));
    }

    @Test
    void testModificarProfesorNoExistente() throws Exception {

        when(profesorService.modificarProfesor(eq(999L), any(ProfesorDto.class)))
            .thenReturn(Optional.empty());
        
 
        mockMvc.perform(put("/profesor/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"No\",\"apellido\":\"Existe\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProfesorExitoso() throws Exception {
     
        when(profesorService.eliminarProfesor(1L)).thenReturn(true);
        
     
        mockMvc.perform(delete("/profesor/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarProfesorNoExistente() throws Exception {
    
        when(profesorService.eliminarProfesor(999L)).thenReturn(false);
        
   
        mockMvc.perform(delete("/profesor/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testObtenerTodosLosProfesores() throws Exception {
    
        List<Profesor> profesores = Arrays.asList(
            new Profesor("Juan", "Pérez"),
            new Profesor("Ana", "García")
        );
        profesores.get(0).setId(1L);
        profesores.get(1).setId(2L);
        
        when(profesorService.obtenerTodos()).thenReturn(profesores);
        
  
        mockMvc.perform(get("/profesor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].nombre").value("Ana"));
    }

    @Test
    void testObtenerMateriasPorProfesorExitoso() throws Exception {
     
        Profesor profesor = new Profesor("Juan", "Pérez");
        profesor.setId(1L);
        
        List<Materia> materias = Arrays.asList(
            new Materia("Álgebra", 1, 2, profesor),
            new Materia("Matemática I", 1, 1, profesor)
        );
        
        when(profesorService.buscarPorId(1L)).thenReturn(Optional.of(profesor));
        when(profesorService.obtenerMateriasPorProfesor(1L)).thenReturn(materias);
        
       
        mockMvc.perform(get("/profesor/1/materias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerMateriasPorProfesorNoExistente() throws Exception {
        
        when(profesorService.buscarPorId(999L)).thenReturn(Optional.empty());
        
       
        mockMvc.perform(get("/profesor/999/materias"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testObtenerMateriasPorProfesorSinMaterias() throws Exception {

        Profesor profesor = new Profesor("Sin", "Materias");
        profesor.setId(1L);
        
        when(profesorService.buscarPorId(1L)).thenReturn(Optional.of(profesor));
        when(profesorService.obtenerMateriasPorProfesor(1L)).thenReturn(Arrays.asList());
        
        
        mockMvc.perform(get("/profesor/1/materias"))
                .andExpect(status().isNoContent());
    }
}