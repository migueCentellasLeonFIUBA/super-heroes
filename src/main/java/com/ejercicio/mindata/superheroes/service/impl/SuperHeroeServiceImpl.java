package com.ejercicio.mindata.superheroes.service.impl;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.repository.SuperHeroeRepository;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperHeroeServiceImpl implements SuperHeroeService {

    @Autowired
    private SuperHeroeRepository superHeroeRepository;

    @Override
    public  Optional<SuperHeroe> getSuperHeroeById(long superHeroeId) {
        return superHeroeRepository.findById(superHeroeId);
    }

    @Override
    public List<SuperHeroe> getAllSuperHeroes() {
        return superHeroeRepository.findAll();
    }
}
