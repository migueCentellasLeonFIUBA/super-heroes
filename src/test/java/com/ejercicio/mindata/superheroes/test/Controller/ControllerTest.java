package com.ejercicio.mindata.superheroes.test.Controller;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private SuperHeroeService superHeroeService;

    @Test
    void testPruebaInicial() throws Exception{
        //given
        List<Object> listSuperHeroes = new ArrayList<>();

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
        List<SuperHeroe> listSuperHeroes = new ArrayList<>();
        listSuperHeroes.add(SuperHeroe.builder().nombre("SpiderMan").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Batman").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("SuperMan").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Flash").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("IronMan").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Hulk").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("CapitanAmerica").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Wolverine").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("WarMachine").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("CapitanComando").build());

        given(superHeroeService.getAllSuperHeroes()).willReturn(listSuperHeroes);

        //when
        ResultActions response = mockMvc.perform(get("/api/superHeroes"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listSuperHeroes.size())))
        ;
    }

}
