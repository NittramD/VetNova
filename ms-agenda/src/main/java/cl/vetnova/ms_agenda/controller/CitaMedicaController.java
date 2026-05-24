package cl.vetnova.ms_agenda.controller;

import cl.vetnova.ms_agenda.dto.CitaRequestDTO;
import cl.vetnova.ms_agenda.dto.CitaResponseDTO;
import cl.vetnova.ms_agenda.service.CitaMedicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CitaMedicaController
 *
 * Autor: Martin Diaz
 */
@RestController
@RequestMapping("/api/agenda")
@RequiredArgsConstructor
public class CitaMedicaController {

    private final CitaMedicaService service;

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> agendar(@Valid @RequestBody CitaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.agendar(dto));
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<CitaResponseDTO> reprogramar(@PathVariable Long id, @Valid @RequestBody CitaRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.reprogramar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        try {
            service.cancelar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}