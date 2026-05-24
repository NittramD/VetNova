package cl.vetnova.ms_mascotas.service;

import cl.vetnova.ms_mascotas.dto.MascotaRequestDTO;
import cl.vetnova.ms_mascotas.dto.MascotaResponseDTO;
import cl.vetnova.ms_mascotas.model.Mascota;
import cl.vetnova.ms_mascotas.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository repository;

    public List<MascotaResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<MascotaResponseDTO> obtenerPorCliente(Long clienteId) {
        log.info("Consultando mascotas del cliente id={}", clienteId);
        return repository.findByClienteId(clienteId).stream().map(this::mapToDTO).toList();
    }

    public Optional<MascotaResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public MascotaResponseDTO registrarMascota(MascotaRequestDTO dto) {
        log.info("Registrando nueva mascota para clienteId={}", dto.getClienteId());
        Mascota mascota = new Mascota(null, dto.getNombre(), dto.getEspecie(), 
                dto.getRaza(), dto.getFechaNacimiento(), dto.getClienteId());
        
        return mapToDTO(repository.save(mascota));
    }
    
    public MascotaResponseDTO actualizarMascota(Long id, MascotaRequestDTO dto) {
        Mascota mascota = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
        
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setFechaNacimiento(dto.getFechaNacimiento());
        mascota.setClienteId(dto.getClienteId());
        
        return mapToDTO(repository.save(mascota));
    }

    public void eliminar(Long id) {
        log.info("Eliminando registro de mascota id={}", id);
        repository.deleteById(id);
    }

    private MascotaResponseDTO mapToDTO(Mascota m) {
        return new MascotaResponseDTO(m.getId(), m.getNombre(), m.getEspecie(), 
                m.getRaza(), m.getFechaNacimiento(), m.getClienteId());
    }
}