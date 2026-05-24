package cl.vetnova.ms_sucursales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MsSucursalesApplication
 *
 * Autor: Martin Diaz
 *
 * Microservicio que administra la información física de las clínicas,
 * horarios de atención, boxes disponibles y personal asignado.
 */
@SpringBootApplication
public class MsSucursalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsSucursalesApplication.class, args);
    }
}