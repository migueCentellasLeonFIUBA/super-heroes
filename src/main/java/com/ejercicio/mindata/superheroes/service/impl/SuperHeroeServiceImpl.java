package com.ejercicio.mindata.superheroes.service.impl;

import com.ejercicio.mindata.superheroes.exception.ResourceNotFoundException;
import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import com.ejercicio.mindata.superheroes.repository.SuperHeroeRepository;
import com.ejercicio.mindata.superheroes.service.SuperHeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SuperHeroeServiceImpl implements SuperHeroeService {

    @Autowired
    private SuperHeroeRepository superHeroeRepository;

    @Override
    @Transactional(readOnly = true)
    public  Optional<SuperHeroe> getSuperHeroeById(long superHeroeId) {
        return superHeroeRepository.findById(superHeroeId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("superHeroes")
    public List<SuperHeroe> getAllSuperHeroes() {
        return superHeroeRepository.findAll();
    }

    @Override
    public List<SuperHeroe> getSuperHeroePorPalabra(String palabra) {
        return superHeroeRepository.findByNombreContainingIgnoreCase(palabra);
    }

    @Override
    @Transactional
    public SuperHeroe updateSuperHeroe(SuperHeroe superHeroe) {
        return superHeroeRepository.save(superHeroe);
    }

    @Caching(evict = {
            @CacheEvict(value = "superHeroes", allEntries = true)
    })
    @Override
    @Transactional
    public Optional<SuperHeroe> updateSuperHeroe(long superHeroeId, SuperHeroe superHeroe) {
       Optional<SuperHeroe> superHeroeObtenido = superHeroeRepository.findById(superHeroeId);
        if(superHeroeObtenido.isEmpty()){
            throw new ResourceNotFoundException("No existe SuperHeroe con Id: "+ superHeroeId);
        }
       return superHeroeObtenido.map(superHeroeSaved -> {
            superHeroeSaved.setNombre(superHeroe.getNombre());
            superHeroeSaved.setCreador(superHeroe.getCreador());
            SuperHeroe superHeroeUp = updateSuperHeroe(superHeroeSaved);
            return Optional.of(superHeroeUp);
        }).orElseGet(Optional::empty);

    }

  @Caching(evict = {
            @CacheEvict(value = "superHeroes", allEntries = true)
    })
    @Transactional
    @Override
    public void deleteSuperHeroe(long superHeroeId) {
        superHeroeRepository.deleteById(superHeroeId);
    }

}
