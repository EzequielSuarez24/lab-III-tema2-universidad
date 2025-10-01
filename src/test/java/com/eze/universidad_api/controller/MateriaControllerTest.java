package com.eze.universidad_api.controller;

import com.eze.universidad_api.model.Materia;
import com.eze.universidad_api.service.MateriaService;
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

@WebMvcTest(MateriaController.class)
class MateriaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private MateriaService materiaService;

    @Test
    void testCrearMateria() throws Exception {
        Materia materia = new Materia();
        materia.setId(1L);
        
        when(materiaService.crearMateria(any())).thenReturn(Optional.of(materia));
        
        mockMvc.perform(post("/materia")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Math\",\"anio\":1,\"cuatrimestre\":1,\"profesorId\":1,\"correlatividades\":[]}"))
                .andExpect(status().isCreated());
    }
}