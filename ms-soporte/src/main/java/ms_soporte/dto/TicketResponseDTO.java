package ms_soporte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Long id;
    private String nombreCliente;
    private String asunto;
    private String detalle;
    private String estado;
    private Integer valoracion;
    private LocalDateTime fechaCreacion;
}