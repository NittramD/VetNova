package cl.vetnova.ms_fichas_clinicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FichaRequestDTO {

    @NotNull(message = "El mascotaId es obligatorio")
    private Long mascotaId;

    @NotNull(message = "El citaMedicaId es obligatorio")
    private Long citaMedicaId;

    @NotNull(message = "El veterinarioId es obligatorio")
    private Long veterinarioId;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    private String tratamiento;
    
    private String receta;
}