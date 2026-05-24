package cl.vetnova.ms_sucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String horarioAtencion;
    private Integer boxesDisponibles;
    private Integer personalAsignado;
}