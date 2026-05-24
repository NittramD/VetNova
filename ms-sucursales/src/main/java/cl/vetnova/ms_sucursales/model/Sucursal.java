package cl.vetnova.ms_sucursales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre; // Ej: Chillán, Los Ángeles, Talca

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(nullable = false, length = 200)
    private String direccion;

    @NotBlank(message = "El horario de atención es obligatorio")
    @Column(nullable = false, length = 100)
    private String horarioAtencion;

    @NotNull(message = "La cantidad de boxes es obligatoria")
    @Min(value = 1, message = "Debe haber al menos 1 box disponible")
    @Column(nullable = false)
    private Integer boxesDisponibles;

    @NotNull(message = "La cantidad de personal asignado es obligatoria")
    @Min(value = 1, message = "Debe haber al menos 1 persona asignada")
    @Column(nullable = false)
    private Integer personalAsignado;
}