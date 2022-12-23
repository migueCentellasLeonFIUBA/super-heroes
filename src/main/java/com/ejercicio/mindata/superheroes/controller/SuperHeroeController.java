package com.ejercicio.mindata.superheroes.controller;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("porPalabra/{palabra}")
    public List<SuperHeroe> obtenerSuperHeroePorPalabra(@PathVariable("palabra") String palabra){
        return superHeroeService.getSuperHeroePorPalabra(palabra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuperHeroe> actualizarEmpleado(@PathVariable("id") long superHeroeId,@RequestBody SuperHeroe superHeroe){
        return superHeroeService.getSuperHeroeById(superHeroeId)
                .map(superHeroeSaved -> {
                    superHeroeSaved.setNombre(superHeroe.getNombre());
                    superHeroeSaved.setCreador(superHeroe.getCreador());
                    SuperHeroe superHeroeUp = superHeroeService.updateSuperHeroe(superHeroeSaved);
                    return new ResponseEntity<>(superHeroeUp, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
