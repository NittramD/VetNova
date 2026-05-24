package cl.vetnova.ms_sucursales.repository;

import cl.vetnova.ms_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    Optional<Sucursal> findByNombreIgnoreCase(String nombre);
}