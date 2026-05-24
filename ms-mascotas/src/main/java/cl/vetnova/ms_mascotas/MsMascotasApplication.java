package cl.vetnova.ms_mascotas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MsMascotasApplication
 *
 * Autor: Martin Diaz
 *
 * Microservicio encargado de gestionar el registro y la información 
 * básica de los pacientes (mascotas) de la clínica VetNova.
 */
@SpringBootApplication
public class MsMascotasApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsMascotasApplication.class, args);
    }
}