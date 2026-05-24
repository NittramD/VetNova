package ms_notificaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificaciones")
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String destinatario; // Email o teléfono

    @Column(nullable = false, length = 100)
    private String tipo; // "EMAIL", "SMS", "WHATSAPP"

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false, length = 50)
    private String estado; // "ENVIADO", "FALLIDO"

    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void onCreate() {
        fechaEnvio = LocalDateTime.now();
    }
}