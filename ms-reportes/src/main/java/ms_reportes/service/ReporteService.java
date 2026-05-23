package ms_reportes.service;

import ms_reportes.dto.ReporteRequestDTO;
import ms_reportes.dto.ReporteResponseDTO;
import ms_reportes.model.Reporte;
import ms_reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public List<ReporteResponseDTO> obtenerTodos() {
        return reporteRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public ReporteResponseDTO generarReporte(ReporteRequestDTO dto) {
        log.info("Generando nuevo reporte para el módulo: {}", dto.getModulo());
        Reporte reporte = new Reporte(null, dto.getModulo(), dto.getTitulo(), dto.getResumenDatos(), null);
        return mapToDTO(reporteRepository.save(reporte));
    }

    private ReporteResponseDTO mapToDTO(Reporte r) {
        return new ReporteResponseDTO(r.getId(), r.getModulo(), r.getTitulo(), 
                                      r.getResumenDatos(), r.getFechaGeneracion());
    }
}