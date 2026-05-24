package cl.vetnova.ms_usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String email;
    private String rol;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}