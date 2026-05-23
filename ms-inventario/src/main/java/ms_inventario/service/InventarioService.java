package ms_inventario.service;

import ms_inventario.dto.InsumoRequestDTO;
import ms_inventario.dto.InsumoResponseDTO;
import ms_inventario.model.Categoria;
import ms_inventario.model.Insumo;
import ms_inventario.repository.CategoriaRepository;
import ms_inventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InsumoRepository insumoRepository;
    private final CategoriaRepository categoriaRepository;

    // Obtener todos los insumos (GET)
    public List<InsumoResponseDTO> obtenerTodos() {
        return insumoRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    // Obtener por ID (GET por id)
    public Optional<InsumoResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando insumo con id={}", id);
        return insumoRepository.findById(id).map(this::mapToDTO);
    }

    // Guardar nuevo insumo (POST)
    public InsumoResponseDTO guardar(InsumoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + dto.getCategoriaId()));
        
        Insumo insumo = new Insumo(null, dto.getNombre(), dto.getStock(), dto.getProveedor(), categoria);
        log.info("Guardando nuevo insumo: {}", dto.getNombre());
        return mapToDTO(insumoRepository.save(insumo));
    }

    // Actualizar insumo existente (PUT)
    public InsumoResponseDTO actualizar(Long id, InsumoRequestDTO dto) {
        Insumo insumoExistente = insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con id: " + id));
        
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + dto.getCategoriaId()));

        insumoExistente.setNombre(dto.getNombre());
        insumoExistente.setStock(dto.getStock());
        insumoExistente.setProveedor(dto.getProveedor());
        insumoExistente.setCategoria(categoria);
        
        log.info("Actualizando insumo id={}", id);
        return mapToDTO(insumoRepository.save(insumoExistente));
    }

    // Eliminar insumo (DELETE)
    public void eliminar(Long id) {
        if (!insumoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Insumo no encontrado con id: " + id);
        }
        log.info("Eliminando insumo id={}", id);
        insumoRepository.deleteById(id);
    }

    // Convertidor de Entidad a DTO para cumplir la rúbrica
    private InsumoResponseDTO mapToDTO(Insumo i) {
        return new InsumoResponseDTO(i.getId(), i.getNombre(), i.getStock(), 
                                    i.getProveedor(), i.getCategoria().getNombre());
    }
}