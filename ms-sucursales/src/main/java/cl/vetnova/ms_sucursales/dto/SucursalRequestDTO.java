package cl.vetnova.ms_sucursales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SucursalRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El horario de atención es obligatorio")
    private String horarioAtencion;

    @NotNull(message = "La cantidad de boxes es obligatoria")
    @Min(value = 1, message = "Debe especificar al menos 1 box")
    private Integer boxesDisponibles;

    @NotNull(message = "El personal asignado es obligatorio")
    @Min(value = 1, message = "Debe especificar al menos 1 persona de personal")
    private Integer personalAsignado;
}