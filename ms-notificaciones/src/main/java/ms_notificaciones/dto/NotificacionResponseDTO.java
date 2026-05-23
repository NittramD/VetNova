package ms_notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponseDTO {
    private Long id;
    private String destinatario;
    private String tipo;
    private String mensaje;
    private String estado;
    private LocalDateTime fechaEnvio;
}