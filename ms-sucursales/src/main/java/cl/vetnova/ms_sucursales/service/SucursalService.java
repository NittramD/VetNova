package cl.vetnova.ms_sucursales.service;

import cl.vetnova.ms_sucursales.dto.SucursalRequestDTO;
import cl.vetnova.ms_sucursales.dto.SucursalResponseDTO;
import cl.vetnova.ms_sucursales.model.Sucursal;
import cl.vetnova.ms_sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository repository;

    public List<SucursalResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<SucursalResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public SucursalResponseDTO crearSucursal(SucursalRequestDTO dto) {
        log.info("Registrando nueva sucursal: {}", dto.getNombre());
        Sucursal sucursal = new Sucursal(null, dto.getNombre(), dto.getDireccion(),
                dto.getHorarioAtencion(), dto.getBoxesDisponibles(), dto.getPersonalAsignado());
        
        return mapToDTO(repository.save(sucursal));
    }
    
    public SucursalResponseDTO actualizarSucursal(Long id, SucursalRequestDTO dto) {
        Sucursal sucursal = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con id: " + id));
        
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setHorarioAtencion(dto.getHorarioAtencion());
        sucursal.setBoxesDisponibles(dto.getBoxesDisponibles());
        sucursal.setPersonalAsignado(dto.getPersonalAsignado());
        
        log.info("Actualizando información de la sucursal id={}", id);
        return mapToDTO(repository.save(sucursal));
    }

    public void eliminar(Long id) {
        log.info("Eliminando sucursal id={}", id);
        repository.deleteById(id);
    }

    private SucursalResponseDTO mapToDTO(Sucursal s) {
        return new SucursalResponseDTO(s.getId(), s.getNombre(), s.getDireccion(), 
                s.getHorarioAtencion(), s.getBoxesDisponibles(), s.getPersonalAsignado());
    }
}