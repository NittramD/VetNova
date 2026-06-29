package ms_soporte;

import ms_soporte.dto.TicketRequestDTO;
import ms_soporte.dto.TicketResponseDTO;
import ms_soporte.model.Ticket;
import ms_soporte.repository.TicketRepository;
import ms_soporte.service.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TicketService - Pruebas Unitarias")
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("crearTicket: guarda correctamente un nuevo ticket y lo deja ABIERTO")
    void crearTicket_deberiaGuardarYRetornarTicket() {
        
        TicketRequestDTO request = new TicketRequestDTO();
        request.setNombreCliente("Franco");
        request.setAsunto("Problema con portal");
        request.setDetalle("No me carga la web");

        
        Ticket ticketGuardado = new Ticket(1L, "Franco", "Problema con portal", "No me carga la web", "ABIERTO", null, LocalDateTime.now());
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketGuardado);

        
        TicketResponseDTO resultado = ticketService.crearTicket(request);

        
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreCliente()).isEqualTo("Franco");
        assertThat(resultado.getEstado()).isEqualTo("ABIERTO");
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    @DisplayName("cerrarTicket: cierra el ticket correctamente y asigna valoración")
    void cerrarTicket_existe_cierraYAsignaValoracion() {
        
        Ticket ticketExistente = new Ticket(1L, "Franco", "Consulta", "Detalle", "ABIERTO", null, LocalDateTime.now());
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticketExistente));
        
        Ticket ticketCerrado = new Ticket(1L, "Franco", "Consulta", "Detalle", "CERRADO", 5, LocalDateTime.now());
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketCerrado);

        
        TicketResponseDTO resultado = ticketService.cerrarTicket(1L, 5);

        
        assertThat(resultado.getEstado()).isEqualTo("CERRADO");
        assertThat(resultado.getValoracion()).isEqualTo(5);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    @DisplayName("cerrarTicket: lanza excepción si el ticket no existe en la base de datos")
    void cerrarTicket_noExiste_lanzaExcepcion() {
        
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> ticketService.cerrarTicket(99L, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ticket no encontrado");
    }
}