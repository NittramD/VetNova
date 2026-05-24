package cl.vetnova.ms_agenda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponseDTO {
    private Long id;
    private Long mascotaId;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
}