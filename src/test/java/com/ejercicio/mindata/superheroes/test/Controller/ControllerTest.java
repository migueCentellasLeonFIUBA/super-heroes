package com.ejercicio.mindata.superheroes.test.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPruebaInicial() throws Exception{
        //given
        List<Object> listaEmpleados = new ArrayList<>();

        //when
        ResultActions response = mockMvc.perform(get("/listar"));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print())
        //.andExpect(jsonPath("$.size()",is(9)))
        ;
    }

    @Test
    void testConsultarTodosLosHeroes() throws Exception{
        //given
        List<Object> listaSuperHeroes = new ArrayList<>();

        //when
        ResultActions response = mockMvc.perform(get("/listar"));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print())
        //.andExpect(jsonPath("$.size()",is(9)))
        ;
    }

}
