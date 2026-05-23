package ms_notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificacionRequestDTO {
    
    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    private String tipo;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;
}