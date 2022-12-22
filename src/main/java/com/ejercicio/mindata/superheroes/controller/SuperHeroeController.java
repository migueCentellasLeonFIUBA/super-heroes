package com.ejercicio.mindata.superheroes.controller;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/superHeroes")
public class SuperHeroeController {

    @Autowired
    private SuperHeroeService superHeroeService;

    @GetMapping
    public List<SuperHeroe> listarSuperHeroes(){
        return superHeroeService.getAllSuperHeroes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperHeroe> obtenerSuperHeroePorId(@PathVariable("id") long superHeroeId){
        return superHeroeService.getSuperHeroeById(superHeroeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
