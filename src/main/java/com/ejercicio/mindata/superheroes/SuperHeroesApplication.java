package com.ejercicio.mindata.superheroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SuperHeroesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperHeroesApplication.class, args);
	}

}
