package cl.vetnova.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ApiGatewayApplication
 *
 * Autor: Martin Diaz
 *
 * Microservicio encargado de recibir las peticiones del cliente (Frontend/Mobile)
 * y enrutarlas a los microservicios correspondientes del backend.
 */
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}