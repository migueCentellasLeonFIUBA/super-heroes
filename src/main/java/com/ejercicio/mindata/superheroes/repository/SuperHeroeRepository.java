package com.ejercicio.mindata.superheroes.repository;

import com.ejercicio.mindata.superheroes.model.SuperHeroe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperHeroeRepository extends JpaRepository<SuperHeroe,Long> {

}
