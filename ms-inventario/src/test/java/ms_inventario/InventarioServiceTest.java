package ms_inventario;

import ms_inventario.model.Insumo;
import ms_inventario.repository.InsumoRepository;
import ms_inventario.model.Categoria;
import ms_inventario.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InsumoRepository insumoRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void obtenerTodos_deberiaRetornarLista() {
    
    Categoria categoria = new Categoria(1L, "Farmacia", "Medicamentos de uso clínico");
    
    
    Insumo insumo = new Insumo(1L, "Bravecto", 50, "MSD", categoria);
    
    
    when(insumoRepository.findAll()).thenReturn(List.of(insumo));

    
    var resultado = inventarioService.obtenerTodos();

    
    assertThat(resultado).hasSize(1);
    assertThat(resultado.get(0).getCategoriaNombre()).isEqualTo("Farmacia"); 
    }
}