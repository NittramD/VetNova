package ms_notificaciones.controller;

import ms_notificaciones.dto.NotificacionRequestDTO;
import ms_notificaciones.dto.NotificacionResponseDTO;
import ms_notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(notificacionService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> enviar(@Valid @RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(201).body(notificacionService.enviarNotificacion(dto));
    }
}