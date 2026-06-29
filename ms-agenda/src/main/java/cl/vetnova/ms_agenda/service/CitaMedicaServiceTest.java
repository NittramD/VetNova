package cl.vetnova.ms_agenda.service;

import cl.vetnova.ms_agenda.client.NotificacionClient;
import cl.vetnova.ms_agenda.client.NotificacionRequest;
import cl.vetnova.ms_agenda.dto.CitaRequestDTO;
import cl.vetnova.ms_agenda.dto.CitaResponseDTO;
import cl.vetnova.ms_agenda.model.CitaMedica;
import cl.vetnova.ms_agenda.repository.CitaMedicaRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaMedicaServiceTest {

    @Mock
    private CitaMedicaRepository repository;

    @Mock
    private NotificacionClient notificacionClient;

    @InjectMocks
    private CitaMedicaService service;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private CitaMedica citaFake(Long id, String estado) {
        return new CitaMedica(
            id,
            faker.number().randomNumber(3, false),
            LocalDateTime.now().plusDays(faker.number().numberBetween(1, 30)),
            faker.lorem().sentence(4),
            estado
        );
    }

    private CitaRequestDTO requestFake() {
        CitaRequestDTO dto = new CitaRequestDTO();
        dto.setMascotaId(faker.number().randomNumber(3, false));
        dto.setFechaHora(LocalDateTime.now().plusDays(faker.number().numberBetween(1, 30)));
        dto.setMotivo(faker.lorem().sentence(4));
        return dto;
    }

    // ── obtenerTodas ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodas: retorna lista con todas las citas")
    void obtenerTodas_retornaLista() {
        List<CitaMedica> citas = List.of(citaFake(1L, "AGENDADA"), citaFake(2L, "AGENDADA"));
        when(repository.findAll()).thenReturn(citas);

        List<CitaResponseDTO> resultado = service.obtenerTodas();

        assertThat(resultado).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("obtenerTodas: retorna lista vacía si no hay citas")
    void obtenerTodas_listaVacia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.obtenerTodas()).isEmpty();
    }

    // ── obtenerPorId ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerPorId: retorna la cita cuando existe")
    void obtenerPorId_encontrada() {
        CitaMedica cita = citaFake(1L, "AGENDADA");
        when(repository.findById(1L)).thenReturn(Optional.of(cita));

        Optional<CitaResponseDTO> resultado = service.obtenerPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("obtenerPorId: retorna vacío cuando no existe")
    void obtenerPorId_noEncontrada() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThat(service.obtenerPorId(99L)).isEmpty();
    }

    // ── agendar ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("agendar: guarda la cita con estado AGENDADA")
    void agendar_guardaConEstadoAgendada() {
        CitaRequestDTO dto = requestFake();
        CitaMedica guardada = citaFake(1L, "AGENDADA");
        when(repository.save(any())).thenReturn(guardada);

        CitaResponseDTO resultado = service.agendar(dto);

        assertThat(resultado.getEstado()).isEqualTo("AGENDADA");
        verify(repository).save(any(CitaMedica.class));
    }

    @Test
    @DisplayName("agendar: llama a notificaciones tras guardar")
    void agendar_enviaNotificacion() {
        CitaRequestDTO dto = requestFake();
        when(repository.save(any())).thenReturn(citaFake(1L, "AGENDADA"));

        service.agendar(dto);

        verify(notificacionClient).enviar(any(NotificacionRequest.class));
    }

    @Test
    @DisplayName("agendar: si notificaciones falla, la cita igual se guarda")
    void agendar_notificacionFalla_citaIgualGuardada() {
        CitaRequestDTO dto = requestFake();
        CitaMedica guardada = citaFake(1L, "AGENDADA");
        when(repository.save(any())).thenReturn(guardada);
        doThrow(new RuntimeException("ms-notificaciones caído"))
            .when(notificacionClient).enviar(any());

        // No debe lanzar excepción
        assertThatCode(() -> service.agendar(dto)).doesNotThrowAnyException();
        verify(repository).save(any());
    }

    // ── reprogramar ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("reprogramar: actualiza fecha y estado a REPROGRAMADA")
    void reprogramar_actualizaEstado() {
        CitaMedica cita = citaFake(1L, "AGENDADA");
        CitaRequestDTO dto = requestFake();
        when(repository.findById(1L)).thenReturn(Optional.of(cita));
        when(repository.save(any())).thenReturn(cita);

        CitaResponseDTO resultado = service.reprogramar(1L, dto);

        assertThat(resultado.getEstado()).isEqualTo("REPROGRAMADA");
        verify(repository).save(cita);
    }

    @Test
    @DisplayName("reprogramar: lanza excepción si la cita no existe")
    void reprogramar_citaNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.reprogramar(99L, requestFake()))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("99");
    }

    // ── cancelar ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("cancelar: cambia estado a CANCELADA")
    void cancelar_cambiaEstado() {
        CitaMedica cita = citaFake(1L, "AGENDADA");
        when(repository.findById(1L)).thenReturn(Optional.of(cita));
        when(repository.save(any())).thenReturn(cita);

        service.cancelar(1L);

        assertThat(cita.getEstado()).isEqualTo("CANCELADA");
        verify(repository).save(cita);
    }

    @Test
    @DisplayName("cancelar: lanza excepción si la cita no existe")
    void cancelar_citaNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.cancelar(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("99");
    }
}