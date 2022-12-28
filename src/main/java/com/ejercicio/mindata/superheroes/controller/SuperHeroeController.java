package com.ejercicio.mindata.superheroes.controller;

import com.ejercicio.mindata.superheroes.logging.LogExecutionTime;
import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superHeroes")
public class SuperHeroeController {

    private final SuperHeroeService superHeroeService;

    public SuperHeroeController (SuperHeroeService superHeroeService){
        this.superHeroeService = superHeroeService;
    }

    @GetMapping
    @LogExecutionTime
    public List<SuperHeroe> listarSuperHeroes(){
        return superHeroeService.getAllSuperHeroes();
    }

    @GetMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> obtenerSuperHeroePorId(@PathVariable("id") long superHeroeId){
        return superHeroeService.getSuperHeroeById(superHeroeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("porPalabra/{palabra}")
    @LogExecutionTime
    public List<SuperHeroe> obtenerSuperHeroePorPalabra(@PathVariable("palabra") String palabra){
        return superHeroeService.getSuperHeroePorPalabra(palabra);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> actualizarSuperHeroe(@PathVariable("id") long superHeroeId,@RequestBody SuperHeroe superHeroe){
        return superHeroeService.updateSuperHeroe(superHeroeId,superHeroe)
                .map(superHeroeSaved -> new ResponseEntity<>(superHeroeSaved, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> eliminarSuperHeroe(@PathVariable("id") long superHeroeId){
        superHeroeService.deleteSuperHeroe(superHeroeId);
        return new ResponseEntity<String>("SuperHeroe eliminado exitosamente",HttpStatus.OK);
    }
}
