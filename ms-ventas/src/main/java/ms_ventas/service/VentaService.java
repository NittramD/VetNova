package ms_ventas.service;

import ms_ventas.dto.VentaRequestDTO;
import ms_ventas.dto.VentaResponseDTO;
import ms_ventas.model.Venta;
import ms_ventas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;

    public List<VentaResponseDTO> obtenerTodas() {
        return ventaRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<VentaResponseDTO> obtenerPorId(Long id) {
        return ventaRepository.findById(id).map(this::mapToDTO);
    }

    public VentaResponseDTO registrarVenta(VentaRequestDTO dto) {
        Venta venta = new Venta(null, dto.getNombreCliente(), dto.getInsumoId(), dto.getCantidad(), dto.getTotalPagado(), null);
        log.info("Registrando nueva venta para el cliente: {}", dto.getNombreCliente());
        return mapToDTO(ventaRepository.save(venta));
    }

    private VentaResponseDTO mapToDTO(Venta v) {
        return new VentaResponseDTO(v.getId(), v.getNombreCliente(), v.getInsumoId(), 
                                    v.getCantidad(), v.getTotalPagado(), v.getFechaVenta());
    }
}