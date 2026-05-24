package cl.vetnova.ms_fichas_clinicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MsFichasClinicasApplication
 *
 * Autor: Martin Diaz
 *
 * Microservicio de uso veterinario para historiales, diagnósticos, 
 * tratamientos y recetas.
 */
@SpringBootApplication
public class MsFichasClinicasApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsFichasClinicasApplication.class, args);
    }
}