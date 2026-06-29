package cl.vetnova.ms_agenda.controller;

import cl.vetnova.ms_agenda.dto.CitaRequestDTO;
import cl.vetnova.ms_agenda.dto.CitaResponseDTO;
import cl.vetnova.ms_agenda.service.CitaMedicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CitaMedicaController.class)
class CitaMedicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitaMedicaService service;

    private ObjectMapper mapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private CitaResponseDTO responseFake(Long id, String estado) {
        return new CitaResponseDTO(
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

    // ── GET /api/agenda ───────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/agenda: retorna 200 con lista de citas")
    void obtenerTodas_retorna200() throws Exception {
        when(service.obtenerTodas()).thenReturn(List.of(
            responseFake(1L, "AGENDADA"),
            responseFake(2L, "AGENDADA")
        ));

        mockMvc.perform(get("/api/agenda"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/agenda: retorna 200 con lista vacía")
    void obtenerTodas_listaVacia_retorna200() throws Exception {
        when(service.obtenerTodas()).thenReturn(List.of());

        mockMvc.perform(get("/api/agenda"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/agenda/{id} ──────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/agenda/{id}: retorna 200 cuando existe")
    void obtenerPorId_existe_retorna200() throws Exception {
        CitaResponseDTO cita = responseFake(1L, "AGENDADA");
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(cita));

        mockMvc.perform(get("/api/agenda/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.estado").value("AGENDADA"));
    }

    @Test
    @DisplayName("GET /api/agenda/{id}: retorna 404 cuando no existe")
    void obtenerPorId_noExiste_retorna404() throws Exception {
        when(service.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/agenda/99"))
            .andExpect(status().isNotFound());
    }

    // ── POST /api/agenda ──────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/agenda: retorna 201 con la cita creada")
    void agendar_retorna201() throws Exception {
        CitaRequestDTO request = requestFake();
        CitaResponseDTO response = responseFake(1L, "AGENDADA");
        when(service.agendar(any())).thenReturn(response);

        mockMvc.perform(post("/api/agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.estado").value("AGENDADA"));
    }

    @Test
    @DisplayName("POST /api/agenda: retorna 400 si el body es inválido")
    void agendar_bodyInvalido_retorna400() throws Exception {
        mockMvc.perform(post("/api/agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    // ── PUT /api/agenda/{id}/reprogramar ──────────────────────────────────────

    @Test
    @DisplayName("PUT /api/agenda/{id}/reprogramar: retorna 200 con cita actualizada")
    void reprogramar_retorna200() throws Exception {
        CitaRequestDTO request = requestFake();
        CitaResponseDTO response = responseFake(1L, "REPROGRAMADA");
        when(service.reprogramar(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/agenda/1/reprogramar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("REPROGRAMADA"));
    }

    @Test
    @DisplayName("PUT /api/agenda/{id}/reprogramar: retorna 404 si no existe")
    void reprogramar_noExiste_retorna404() throws Exception {
        when(service.reprogramar(eq(99L), any()))
            .thenThrow(new RuntimeException("Cita no encontrada con id: 99"));

        mockMvc.perform(put("/api/agenda/99/reprogramar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestFake())))
            .andExpect(status().isNotFound());
    }

    // ── DELETE /api/agenda/{id}/cancelar ─────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/agenda/{id}/cancelar: retorna 204")
    void cancelar_retorna204() throws Exception {
        doNothing().when(service).cancelar(1L);

        mockMvc.perform(delete("/api/agenda/1/cancelar"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/agenda/{id}/cancelar: retorna 404 si no existe")
    void cancelar_noExiste_retorna404() throws Exception {
        doThrow(new RuntimeException("Cita no encontrada con id: 99"))
            .when(service).cancelar(99L);

        mockMvc.perform(delete("/api/agenda/99/cancelar"))
            .andExpect(status().isNotFound());
    }
}