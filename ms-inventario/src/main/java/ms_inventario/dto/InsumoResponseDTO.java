package ms_inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoResponseDTO {
    private Long id;
    private String nombre;
    private Integer stock;
    private String proveedor;
    private String categoriaNombre;
}