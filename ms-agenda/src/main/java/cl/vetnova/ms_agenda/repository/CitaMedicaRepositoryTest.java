package cl.vetnova.ms_agenda.repository;

import cl.vetnova.ms_agenda.model.CitaMedica;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CitaMedicaRepositoryTest {

    @Autowired
    private CitaMedicaRepository repository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        repository.deleteAll();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private CitaMedica citaFake(String estado) {
        return new CitaMedica(
            null,
            faker.number().randomNumber(3, false),
            LocalDateTime.now().plusDays(faker.number().numberBetween(1, 30)),
            faker.lorem().sentence(4),
            estado
        );
    }

    // ── save / findById ───────────────────────────────────────────────────────

    @Test
    @DisplayName("save: persiste la cita y genera ID")
    void save_persisteYGeneraId() {
        CitaMedica cita = repository.save(citaFake("AGENDADA"));

        assertThat(cita.getId()).isNotNull();
        assertThat(cita.getEstado()).isEqualTo("AGENDADA");
    }

    @Test
    @DisplayName("findById: encuentra la cita guardada")
    void findById_encuentraCitaGuardada() {
        CitaMedica guardada = repository.save(citaFake("AGENDADA"));

        Optional<CitaMedica> resultado = repository.findById(guardada.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getMotivo()).isEqualTo(guardada.getMotivo());
    }

    @Test
    @DisplayName("findById: retorna vacío para ID inexistente")
    void findById_idInexistente_retornaVacio() {
        assertThat(repository.findById(999L)).isEmpty();
    }

    // ── findAll ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll: retorna todas las citas guardadas")
    void findAll_retornaTodasLasCitas() {
        repository.save(citaFake("AGENDADA"));
        repository.save(citaFake("AGENDADA"));
        repository.save(citaFake("CANCELADA"));

        List<CitaMedica> citas = repository.findAll();

        assertThat(citas).hasSize(3);
    }

    @Test
    @DisplayName("findAll: retorna lista vacía si no hay citas")
    void findAll_sinCitas_retornaVacio() {
        assertThat(repository.findAll()).isEmpty();
    }

    // ── update ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("save: actualiza el estado de una cita existente")
    void save_actualizaEstado() {
        CitaMedica cita = repository.save(citaFake("AGENDADA"));
        cita.setEstado("REPROGRAMADA");
        repository.save(cita);

        CitaMedica actualizada = repository.findById(cita.getId()).orElseThrow();
        assertThat(actualizada.getEstado()).isEqualTo("REPROGRAMADA");
    }

    // ── delete ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deleteAll: elimina todas las citas")
    void deleteAll_eliminaTodo() {
        repository.save(citaFake("AGENDADA"));
        repository.save(citaFake("CANCELADA"));

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}