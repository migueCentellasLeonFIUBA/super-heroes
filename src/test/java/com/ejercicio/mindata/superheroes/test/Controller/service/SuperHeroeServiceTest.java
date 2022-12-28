package com.ejercicio.mindata.superheroes.test.Controller.service;


import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.repository.SuperHeroeRepository;
import com.ejercicio.mindata.superheroes.service.impl.SuperHeroeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SuperHeroeServiceTest {

    @Mock
    private SuperHeroeRepository superHeroeRepository;

    @InjectMocks
    private SuperHeroeServiceImpl superHeroeServiceImpl;

    private SuperHeroe superHeroe;

    @BeforeEach
    void setup(){
        superHeroe = SuperHeroe.builder().nombre("SpiderMan").creador("Marvel").build();

    }

    @DisplayName("Test para listar a los superHeroes")
    @Test
    void testListarSuperHeroes(){
        //given
        SuperHeroe superHeroeSeg = (SuperHeroe.builder().nombre("Wolverine").creador("Marvel").build());
        given(superHeroeRepository.findAll()).willReturn(List.of(superHeroe,superHeroeSeg));

        //when
        List<SuperHeroe> superHeroes = superHeroeServiceImpl.getAllSuperHeroes();

        //then
        assertThat(superHeroes).isNotNull();
        assertThat(superHeroes.size()).isEqualTo(2);
    }

    @DisplayName("Test para eliminar un superHeroe")
    @Test
    void testEliminarEmpleado(){
        //given
        long superHeroeId = 1L;
        willDoNothing().given(superHeroeRepository).deleteById(superHeroeId);

        //when
        superHeroeServiceImpl.deleteSuperHeroe(superHeroeId);

        //then
        verify(superHeroeRepository,times(1)).deleteById(superHeroeId);
    }

    @DisplayName("Test para actualizar un superHeroe")
    @Test
    void testActualizarSuperHeroe(){
        //given
        given(superHeroeRepository.save(superHeroe)).willReturn(superHeroe);
        superHeroe.setNombre("Hombre Araña");
        superHeroe.setCreador("Marvel Comics");

        //when
        SuperHeroe superHeroeActualizado  = superHeroeServiceImpl.updateSuperHeroe(superHeroe);

        //then
        assertThat(superHeroeActualizado.getNombre()).isEqualTo("Hombre Araña");
        assertThat(superHeroeActualizado.getCreador()).isEqualTo("Marvel Comics");
    }

}
