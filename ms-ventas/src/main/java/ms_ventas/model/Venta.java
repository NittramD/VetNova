package ms_ventas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ventas")
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombreCliente;

    @Column(nullable = false)
    private Long insumoId; // Guardamos el ID del insumo que está en el otro microservicio

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer totalPagado; 
    
    private LocalDateTime fechaVenta;

    @PrePersist
    protected void onCreate() {
        fechaVenta = LocalDateTime.now(); // Asigna la fecha automáticamente al vender
    }
}