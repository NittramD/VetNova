package cl.vetnova.ms_fichas_clinicas.repository;

import cl.vetnova.ms_fichas_clinicas.model.FichaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FichaClinicaRepository extends JpaRepository<FichaClinica, Long> {
    List<FichaClinica> findByMascotaId(Long mascotaId);
}