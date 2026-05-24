package cl.vetnova.ms_mascotas.controller;

import cl.vetnova.ms_mascotas.dto.MascotaRequestDTO;
import cl.vetnova.ms_mascotas.dto.MascotaResponseDTO;
import cl.vetnova.ms_mascotas.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MascotaController
 *
 * Autor: Martin Diaz
 */
@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService service;

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.obtenerPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> registrarMascota(@Valid @RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.registrarMascota(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizarMascota(@PathVariable Long id, @Valid @RequestBody MascotaRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizarMascota(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}