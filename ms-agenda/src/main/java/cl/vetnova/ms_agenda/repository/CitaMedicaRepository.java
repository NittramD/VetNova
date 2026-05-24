package cl.vetnova.ms_agenda.repository;

import cl.vetnova.ms_agenda.model.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {
}