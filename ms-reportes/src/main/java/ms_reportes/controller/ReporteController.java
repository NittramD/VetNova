package ms_reportes.controller;

import ms_reportes.dto.ReporteRequestDTO;
import ms_reportes.dto.ReporteResponseDTO;
import ms_reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(reporteService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> generar(@Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.status(201).body(reporteService.generarReporte(dto));
    }
}