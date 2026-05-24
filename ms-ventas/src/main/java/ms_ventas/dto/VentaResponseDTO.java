package ms_ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {
    private Long id;
    private String nombreCliente;
    private Long insumoId;
    private Integer cantidad;
    private Integer totalPagado;
    private LocalDateTime fechaVenta;
}