package cl.vetnova.ms_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MsUsuariosApplication
 *
 * Autor: Martin Diaz
 *
 * Microservicio encargado de gestionar el acceso, creación de cuentas 
 * y perfiles (Administrador, Veterinario, Cliente, Recepcionista).
 */
@SpringBootApplication
public class MsUsuariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }
}