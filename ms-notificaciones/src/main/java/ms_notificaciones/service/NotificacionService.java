package ms_notificaciones.service;

import ms_notificaciones.dto.NotificacionRequestDTO;
import ms_notificaciones.dto.NotificacionResponseDTO;
import ms_notificaciones.model.Notificacion;
import ms_notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public List<NotificacionResponseDTO> obtenerTodas() {
        return notificacionRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public NotificacionResponseDTO enviarNotificacion(NotificacionRequestDTO dto) {
        // Aquí iría la lógica real para conectarse a Gmail, Twilio, etc.
        // Por ahora lo simulamos imprimiendo en la consola:
        log.info("ENVIANDO {} a {}: {}", dto.getTipo(), dto.getDestinatario(), dto.getMensaje());
        
        Notificacion notificacion = new Notificacion(null, dto.getDestinatario(), dto.getTipo(), dto.getMensaje(), "ENVIADO", null);
        return mapToDTO(notificacionRepository.save(notificacion));
    }

    private NotificacionResponseDTO mapToDTO(Notificacion n) {
        return new NotificacionResponseDTO(n.getId(), n.getDestinatario(), n.getTipo(), 
                                           n.getMensaje(), n.getEstado(), n.getFechaEnvio());
    }
}