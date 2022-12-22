package com.ejercicio.mindata.superheroes.service.impl;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.repository.SuperHeroeRepository;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperHeroeServiceImpl implements SuperHeroeService {

    @Autowired
    private SuperHeroeRepository superHeroeRepository;

    @Override
    public List<SuperHeroe> getAllSuperHeroes() {
        return superHeroeRepository.findAll();
    }
}
