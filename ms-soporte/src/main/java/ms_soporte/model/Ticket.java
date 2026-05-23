package ms_soporte.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombreCliente;

    @Column(nullable = false, length = 100)
    private String asunto; // Ej: "Consulta sobre vacuna", "Reclamo por demora"

    @Column(nullable = false, length = 500)
    private String detalle;

    @Column(nullable = false, length = 50)
    private String estado; // "ABIERTO", "EN_PROCESO", "CERRADO"
    
    private Integer valoracion; // Del 1 al 5 (estrellas) cuando se cierra

    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        estado = "ABIERTO"; // Por defecto, todo ticket nuevo nace abierto
    }
}