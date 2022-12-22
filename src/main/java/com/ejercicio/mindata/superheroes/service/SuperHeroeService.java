package com.ejercicio.mindata.superheroes.service;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;

import java.util.List;
import java.util.Optional;

public interface SuperHeroeService {

    Optional<SuperHeroe> getSuperHeroeById(long superHeroeId);

    List<SuperHeroe> getAllSuperHeroes();
}
