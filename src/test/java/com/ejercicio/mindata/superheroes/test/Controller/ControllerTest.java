package com.ejercicio.mindata.superheroes.test.Controller;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Autowired
    private ObjectMapper objectMapper;

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
        listSuperHeroes.add(SuperHeroe.builder().nombre("SpiderMan").creador("Marvel").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Batman").creador("WarnerBros").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("SuperMan").creador("WarnerBros").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Flash").creador("WarnerBros").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("IronMan").creador("Marvel").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Hulk").creador("Marvel").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("CapitanAmerica").creador("Marvel").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("Wolverine").creador("Marvel").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("WarMachine").creador("Marvel").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("CapitanComando").creador("Capcom").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("MegaMan").creador("Capcom").build());

        given(superHeroeService.getAllSuperHeroes()).willReturn(listSuperHeroes);

        //when
        ResultActions response = mockMvc.perform(get("/api/superHeroes"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listSuperHeroes.size())));
    }

    @Test
    void testObtenerSuperHeroePorId() throws Exception{
        //given
        long superHeroeId = 1L;
        SuperHeroe superHeroe = SuperHeroe.builder().nombre("SpiderMan").creador("Marvel").build();
        given(superHeroeService.getSuperHeroeById(superHeroeId)).willReturn(Optional.of(superHeroe));

        //when
        ResultActions response = mockMvc.perform(get("/api/superHeroes/{id}",superHeroeId));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(superHeroe.getNombre())))
                .andExpect(jsonPath("$.creador",is(superHeroe.getCreador())));
    }

    @Test
    void testIntentarObtenerSuperHeroeNoExistente() throws Exception{
        //given
        long empleadoId = 1L;
        SuperHeroe superHeroe = SuperHeroe.builder().nombre("SpiderMan").creador("Marvel").build();
        given(superHeroeService.getSuperHeroeById(empleadoId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/superHeroes/{id}",empleadoId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testObtenerSuperHeroeQueContengaCiertaPalabra() throws Exception{
        //given
        String palabraBuscada = "capitan";
        List<SuperHeroe> listSuperHeroes = new ArrayList<>();
        listSuperHeroes.add(SuperHeroe.builder().nombre("CapitanAmerica").creador("Marvel").build());
        listSuperHeroes.add(SuperHeroe.builder().nombre("CapitanComando").creador("Capcom").build());
        given(superHeroeService.getSuperHeroePorPalabra(palabraBuscada)).willReturn(listSuperHeroes);

        //when
        ResultActions response = mockMvc.perform(get("/api/superHeroes/porPalabra/{palabra}",palabraBuscada));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listSuperHeroes.size())));
    }

    @Test
    void testModificarSuperHeroe() throws Exception{
        //given
        long superHeroeId = 1L;
        SuperHeroe superHeroeInicial = SuperHeroe.builder().nombre("SpiderMan").creador("Marvel").build();
        SuperHeroe superHeroeModificado = SuperHeroe.builder().nombre("HombreAraña").creador("Marvel").build();

        given(superHeroeService.getSuperHeroeById(superHeroeId)).willReturn(Optional.of(superHeroeInicial));
        given(superHeroeService.updateSuperHeroe(any(SuperHeroe.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/superHeroes/{id}",superHeroeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(superHeroeModificado)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(superHeroeModificado.getNombre())))
                .andExpect(jsonPath("$.creador",is(superHeroeModificado.getCreador())));
    }
}
