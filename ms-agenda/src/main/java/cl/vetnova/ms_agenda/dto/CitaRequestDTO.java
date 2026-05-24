package cl.vetnova.ms_agenda.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaRequestDTO {

    @NotNull(message = "El mascotaId es obligatorio")
    private Long mascotaId;

    @NotNull(message = "La fecha y hora de la cita es obligatoria")
    @Future(message = "La cita debe programarse en el futuro")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;
}