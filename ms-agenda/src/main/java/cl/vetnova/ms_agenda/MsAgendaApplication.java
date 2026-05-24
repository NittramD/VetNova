package cl.vetnova.ms_agenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * MsAgendaApplication
 *
 * Autor: Martin Diaz
 *
 * Microservicio encargado de agendar, reprogramar y cancelar horas médicas.
 * Se comunica con ms-notificaciones vía FeignClient.
 */
@SpringBootApplication
@EnableFeignClients
public class MsAgendaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsAgendaApplication.class, args);
    }
}