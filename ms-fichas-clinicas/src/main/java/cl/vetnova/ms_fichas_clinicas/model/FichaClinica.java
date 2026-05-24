package cl.vetnova.ms_fichas_clinicas.model;

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
@Table(name = "fichas_clinicas")
public class FichaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la mascota es obligatorio")
    @Column(nullable = false)
    private Long mascotaId;

    @NotNull(message = "El ID de la cita médica es obligatorio")
    @Column(nullable = false, unique = true) // Relación 1:1 conceptual
    private Long citaMedicaId;

    @NotNull(message = "El ID del veterinario es obligatorio")
    @Column(nullable = false)
    private Long veterinarioId;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    private String receta;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaAtencion;
    
    @PrePersist
    protected void onCreate() {
        this.fechaAtencion = LocalDateTime.now();
    }
}