package ms_inventario.controller;

import ms_inventario.dto.InsumoRequestDTO;
import ms_inventario.dto.InsumoResponseDTO;
import ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InsumoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(inventarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return inventarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InsumoResponseDTO> crear(@Valid @RequestBody InsumoRequestDTO dto) {
        return ResponseEntity.status(201).body(inventarioService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody InsumoRequestDTO dto) {
        return ResponseEntity.ok(inventarioService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}