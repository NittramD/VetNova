package cl.vetnova.ms_fichas_clinicas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaResponseDTO {
    private Long id;
    private Long mascotaId;
    private Long citaMedicaId;
    private Long veterinarioId;
    private String diagnostico;
    private String tratamiento;
    private String receta;
    private LocalDateTime fechaAtencion;
}