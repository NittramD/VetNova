package cl.vetnova.ms_agenda.service;

import cl.vetnova.ms_agenda.dto.CitaRequestDTO;
import cl.vetnova.ms_agenda.dto.CitaResponseDTO;
import cl.vetnova.ms_agenda.model.CitaMedica;
import cl.vetnova.ms_agenda.repository.CitaMedicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CitaMedicaService {

    private final CitaMedicaRepository repository;

    public List<CitaResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<CitaResponseDTO> obtenerPorId(Long id) {
        log.info("Consultando cita médica id={}", id);
        return repository.findById(id).map(this::mapToDTO);
    }

    public CitaResponseDTO agendar(CitaRequestDTO dto) {
        log.info("Agendando nueva cita para mascotaId={}", dto.getMascotaId());
        CitaMedica cita = new CitaMedica(null, dto.getMascotaId(), dto.getFechaHora(), dto.getMotivo(), "AGENDADA");
        // TODO: Aquí se podría usar FeignClient para llamar a ms-notificaciones
        return mapToDTO(repository.save(cita));
    }
    
    public CitaResponseDTO reprogramar(Long id, CitaRequestDTO dto) {
        log.info("Reprogramando cita id={}", id);
        CitaMedica cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        
        cita.setFechaHora(dto.getFechaHora());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado("REPROGRAMADA");
        
        return mapToDTO(repository.save(cita));
    }

    public void cancelar(Long id) {
        log.info("Cancelando cita id={}", id);
        CitaMedica cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        cita.setEstado("CANCELADA");
        repository.save(cita);
    }

    private CitaResponseDTO mapToDTO(CitaMedica c) {
        return new CitaResponseDTO(c.getId(), c.getMascotaId(), c.getFechaHora(), c.getMotivo(), c.getEstado());
    }
}