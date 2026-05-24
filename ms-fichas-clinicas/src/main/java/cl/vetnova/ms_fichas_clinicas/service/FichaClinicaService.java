package cl.vetnova.ms_fichas_clinicas.service;

import cl.vetnova.ms_fichas_clinicas.dto.FichaRequestDTO;
import cl.vetnova.ms_fichas_clinicas.dto.FichaResponseDTO;
import cl.vetnova.ms_fichas_clinicas.model.FichaClinica;
import cl.vetnova.ms_fichas_clinicas.repository.FichaClinicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FichaClinicaService {

    private final FichaClinicaRepository repository;

    public List<FichaResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<FichaResponseDTO> obtenerPorMascota(Long mascotaId) {
        log.info("Consultando historial clínico de mascotaId={}", mascotaId);
        return repository.findByMascotaId(mascotaId).stream().map(this::mapToDTO).toList();
    }

    public Optional<FichaResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public FichaResponseDTO registrarFicha(FichaRequestDTO dto) {
        log.info("Registrando nueva atención médica para mascotaId={}", dto.getMascotaId());
        FichaClinica ficha = new FichaClinica();
        ficha.setMascotaId(dto.getMascotaId());
        ficha.setCitaMedicaId(dto.getCitaMedicaId());
        ficha.setVeterinarioId(dto.getVeterinarioId());
        ficha.setDiagnostico(dto.getDiagnostico());
        ficha.setTratamiento(dto.getTratamiento());
        ficha.setReceta(dto.getReceta());
        
        return mapToDTO(repository.save(ficha));
    }
    
    public FichaResponseDTO actualizarFicha(Long id, FichaRequestDTO dto) {
        FichaClinica ficha = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha clínica no encontrada con id: " + id));
        
        ficha.setDiagnostico(dto.getDiagnostico());
        ficha.setTratamiento(dto.getTratamiento());
        ficha.setReceta(dto.getReceta());
        
        return mapToDTO(repository.save(ficha));
    }

    private FichaResponseDTO mapToDTO(FichaClinica f) {
        return new FichaResponseDTO(f.getId(), f.getMascotaId(), f.getCitaMedicaId(), 
                f.getVeterinarioId(), f.getDiagnostico(), f.getTratamiento(), 
                f.getReceta(), f.getFechaAtencion());
    }
}