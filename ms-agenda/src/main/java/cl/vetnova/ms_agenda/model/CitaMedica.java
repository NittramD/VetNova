package cl.vetnova.ms_agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "citas_medicas")
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la mascota es obligatorio")
    @Column(nullable = false)
    private Long mascotaId;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo de la consulta no puede estar vacío")
    @Column(nullable = false, length = 200)
    private String motivo;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false, length = 20)
    private String estado; // Ej: "AGENDADA", "CANCELADA", "REPROGRAMADA"
}