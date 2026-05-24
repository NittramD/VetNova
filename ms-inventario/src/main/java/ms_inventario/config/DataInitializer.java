package ms_inventario.config;

import ms_inventario.model.Categoria;
import ms_inventario.model.Insumo;
import ms_inventario.repository.CategoriaRepository;
import ms_inventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final InsumoRepository insumoRepository;

    @Override
    public void run(String... args) {
        insumoRepository.deleteAll();
        categoriaRepository.deleteAll();

        Categoria farmacia = categoriaRepository.save(new Categoria(null, "Farmacia Veterinaria", "Medicamentos y vacunas de uso clínico"));
        Categoria alimentos = categoriaRepository.save(new Categoria(null, "Alimentos y Dietas", "Comida seca, húmeda y dietas medicadas"));
        Categoria accesorios = categoriaRepository.save(new Categoria(null, "Insumos y Accesorios", "Collares, correas, e insumos de box"));

        insumoRepository.save(new Insumo(null, "Antiparasitario Bravecto (Perros 10-20kg)", 45, "MSD Animal Health", farmacia));
        insumoRepository.save(new Insumo(null, "Vacuna Óctuple Canina", 120, "Zoetis", farmacia));
        insumoRepository.save(new Insumo(null, "Meloxicam Inyectable 2% (Uso Vet)", 30, "Drag Pharma", farmacia));
        insumoRepository.save(new Insumo(null, "Alimento Pro Plan Gato Esterilizado 3kg", 80, "Purina", alimentos));
        insumoRepository.save(new Insumo(null, "Dieta Medicada Royal Canin Renal Perro 2kg", 25, "Royal Canin", alimentos));

        log.info(">>> DataInitializer: Carga forzada de insumos veterinarios exitosa.");
    }
}