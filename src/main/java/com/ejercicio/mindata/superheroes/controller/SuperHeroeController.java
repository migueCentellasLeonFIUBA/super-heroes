package com.ejercicio.mindata.superheroes.controller;

import com.ejercicio.mindata.superheroes.logging.LogExecutionTime;
import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "SuperHeroe Controller", produces = MediaType.APPLICATION_JSON_VALUE, tags = {
        "SuperHeroe Management" })
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

    @ApiOperation(value = "Obtener SuperHeroes por superHeroeId")
    @GetMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> obtenerSuperHeroePorId(@PathVariable("id") long superHeroeId){
        return superHeroeService.getSuperHeroeById(superHeroeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Obtener SuperHeroes por palabra",notes = "Ingresar palbra clave a buscar en SuperHeroes")
    @GetMapping("porPalabra/{palabra}")
    @LogExecutionTime
    public List<SuperHeroe> obtenerSuperHeroePorPalabra(@PathVariable("palabra") String palabra){
        return superHeroeService.getSuperHeroePorPalabra(palabra);
    }

    @ApiOperation(value = "Actualizar SupeHeroe", notes = "Actualizar SuperHeroe a traves de otro similar con diferentes atributos")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> actualizarSuperHeroe(@PathVariable("id") long superHeroeId,@RequestBody SuperHeroe superHeroe){
        return superHeroeService.updateSuperHeroe(superHeroeId,superHeroe)
                .map(superHeroeSaved -> new ResponseEntity<>(superHeroeSaved, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Eliminar SupeHeroe por id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "SuperHeroe eliminado exitosamente") })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> eliminarSuperHeroe(@PathVariable("id") long superHeroeId){
        superHeroeService.deleteSuperHeroe(superHeroeId);
        return new ResponseEntity<String>("SuperHeroe eliminado exitosamente",HttpStatus.OK);
    }
}
