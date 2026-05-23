package ms_reportes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReporteRequestDTO {
    
    @NotBlank(message = "Debe indicar el módulo del reporte (ej: VENTAS)")
    private String modulo;

    @NotBlank(message = "El título del reporte es obligatorio")
    private String titulo;

    @NotBlank(message = "El resumen de datos no puede estar vacío")
    private String resumenDatos;
}