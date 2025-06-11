package com.example.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.rest.controller.PaisController;
import com.example.rest.model.Pais;
import com.example.rest.service.PaisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RestApplicationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PaisService paisService;

    @InjectMocks
    private PaisController paisController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paisController).build();
    }

    @Test
    public void listarPaisesTest() throws Exception {
        List<Pais> paises = Arrays.asList(
                new Pais(1L, "España"),
                new Pais(2L, "Francia")
        );

        when(paisService.obtenerTodos()).thenReturn(paises);

        mockMvc.perform(get("/paises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("España"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Francia"));
    }

    @Test
    public void obtenerPaisPorIdTest_Existe() throws Exception {
        Pais pais = new Pais(1L, "México");
        when(paisService.obtenerPorId(1L)).thenReturn(Optional.of(pais));

        mockMvc.perform(get("/paises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("México"));
    }

    @Test
    public void obtenerPaisPorIdTest_NoExiste() throws Exception {
        when(paisService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/paises/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void obtenerPaisPorNombreTest_Existe() throws Exception {
        Pais pais = new Pais(1L, "Argentina");
        when(paisService.obtenerPorNombre("Argentina")).thenReturn(Optional.of(pais));

        mockMvc.perform(get("/paises/nombre/Argentina"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Argentina"));
    }

    @Test
    public void obtenerPaisPorNombreTest_NoExiste() throws Exception {
        when(paisService.obtenerPorNombre("Desconocido")).thenReturn(Optional.empty());

        mockMvc.perform(get("/paises/nombre/Desconocido"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearPaisTest() throws Exception {
        Pais nuevoPais = new Pais(null, "Chile");
        Pais paisGuardado = new Pais(1L, "Chile");

        when(paisService.guardarPais(any(Pais.class))).thenReturn(paisGuardado);

        mockMvc.perform(post("/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoPais)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Chile"));
    }

    @Test
    public void eliminarPaisTest() throws Exception {
        doNothing().when(paisService).eliminarPais(1L);

        mockMvc.perform(delete("/paises/1"))
                .andExpect(status().isNoContent());
    }
}