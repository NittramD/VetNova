package ms_ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaRequestDTO {
    
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotNull(message = "Debe indicar el ID del insumo a comprar")
    private Long insumoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Debe comprar al menos 1 unidad")
    private Integer cantidad;

    @NotNull(message = "El total pagado es obligatorio")
    @Min(value = 0, message = "El total no puede ser negativo")
    private Integer totalPagado;
}