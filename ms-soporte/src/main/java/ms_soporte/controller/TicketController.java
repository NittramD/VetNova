package ms_soporte.controller;

import ms_soporte.dto.TicketRequestDTO;
import ms_soporte.dto.TicketResponseDTO;
import ms_soporte.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soporte")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(ticketService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> crear(@Valid @RequestBody TicketRequestDTO dto) {
        return ResponseEntity.status(201).body(ticketService.crearTicket(dto));
    }

    // Un endpoint especial para cerrar el ticket con un parámetro en la URL
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<TicketResponseDTO> cerrar(@PathVariable Long id, @RequestParam Integer valoracion) {
        return ResponseEntity.ok(ticketService.cerrarTicket(id, valoracion));
    }
}