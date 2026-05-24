package cl.vetnova.ms_fichas_clinicas.controller;

import cl.vetnova.ms_fichas_clinicas.dto.FichaRequestDTO;
import cl.vetnova.ms_fichas_clinicas.dto.FichaResponseDTO;
import cl.vetnova.ms_fichas_clinicas.service.FichaClinicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FichaClinicaController
 *
 * Autor: Martin Diaz
 */
@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
public class FichaClinicaController {

    private final FichaClinicaService service;

    @GetMapping
    public ResponseEntity<List<FichaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<FichaResponseDTO>> obtenerHistorialMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(service.obtenerPorMascota(mascotaId));
    }

    @PostMapping
    public ResponseEntity<FichaResponseDTO> registrarFicha(@Valid @RequestBody FichaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.registrarFicha(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FichaResponseDTO> actualizarFicha(@PathVariable Long id, @Valid @RequestBody FichaRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizarFicha(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}