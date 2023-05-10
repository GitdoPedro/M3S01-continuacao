package com.example.M3S01.controller;

import com.example.M3S01.dto.VeiculoRequest;
import com.example.M3S01.model.Veiculo;
import com.example.M3S01.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)  //
@WebMvcTest
class VeiculosControllerTest {
    @Autowired
    private MockMvc mockMvc;  // objeto para fazer requisicoes http

    @Autowired
    private ObjectMapper objectMapper; // classe que serializa Objetos para JSON

    private ModelMapper modelMapper = mock(ModelMapper.class); // classe que transforma classes entre si, copiando os atributos

    @MockBean  // mock para dependencias da classe de controller
    private VeiculoService service;


    @Test
    @DisplayName("Quando nao há veículos registrados, deve retornar lista vazia")
    void consultar_vazio() throws Exception {
        mockMvc.perform(get("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)) // response body com JSON
                .andExpect(status().isOk())  // 200
                .andExpect(jsonPath("$", is(empty())));
    }

    @Test
    @DisplayName("Quando inclusao com placa jah existente, deve retornar erro")
    void incluir_jaCadastrado() throws Exception {
        Mockito.when(service.inserir(Mockito.any(Veiculo.class))).thenThrow(RuntimeException.class);
        var req = new VeiculoRequest("ABC-123", "azul", 2005, 5);
        String requestJson = objectMapper.writeValueAsString(req);
        mockMvc.perform(post("/api/veiculos")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())  // 409
                .andExpect(jsonPath("$.erro", containsStringIgnoringCase("Registro já cadastrado!")));
    }



}