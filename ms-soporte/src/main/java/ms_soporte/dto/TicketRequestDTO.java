package ms_soporte.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TicketRequestDTO {
    
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "Debe proporcionar detalles de su consulta/reclamo")
    private String detalle;
}