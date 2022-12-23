package com.ejercicio.mindata.superheroes.service.impl;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.repository.SuperHeroeRepository;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Override
    public List<SuperHeroe> getSuperHeroePorPalabra(String palabra) {
        return superHeroeRepository.findByNombreContainingIgnoreCase(palabra);
    }

    @Override
    public SuperHeroe updateSuperHeroe(SuperHeroe superHeroe) {
        return superHeroeRepository.save(superHeroe);
    }

    @Override
    public Optional<SuperHeroe> updateSuperHeroe(long superHeroeId, SuperHeroe superHeroe) {
       Optional<SuperHeroe> superHeroeObtenido = superHeroeRepository.findById(superHeroeId);
       return superHeroeObtenido.map(superHeroeSaved -> {
            superHeroeSaved.setNombre(superHeroe.getNombre());
            superHeroeSaved.setCreador(superHeroe.getCreador());
            SuperHeroe superHeroeUp = updateSuperHeroe(superHeroeSaved);
            return Optional.of(superHeroeUp);
        }).orElseGet(() -> Optional.empty());

    }

}
