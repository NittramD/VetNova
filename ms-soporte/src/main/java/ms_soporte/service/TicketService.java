package ms_soporte.service;

import ms_soporte.dto.TicketRequestDTO;
import ms_soporte.dto.TicketResponseDTO;
import ms_soporte.model.Ticket;
import ms_soporte.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<TicketResponseDTO> obtenerTodos() {
        return ticketRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public TicketResponseDTO crearTicket(TicketRequestDTO dto) {
        Ticket ticket = new Ticket(null, dto.getNombreCliente(), dto.getAsunto(), dto.getDetalle(), null, null, null);
        log.info("Creando nuevo ticket de soporte para: {}", dto.getNombreCliente());
        return mapToDTO(ticketRepository.save(ticket));
    }

    // Método extra para que el admin cierre el ticket y el cliente ponga nota
    public TicketResponseDTO cerrarTicket(Long id, Integer valoracion) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        ticket.setEstado("CERRADO");
        ticket.setValoracion(valoracion);
        log.info("Cerrando ticket id={} con valoración {}", id, valoracion);
        return mapToDTO(ticketRepository.save(ticket));
    }

    private TicketResponseDTO mapToDTO(Ticket t) {
        return new TicketResponseDTO(t.getId(), t.getNombreCliente(), t.getAsunto(), 
                                     t.getDetalle(), t.getEstado(), t.getValoracion(), t.getFechaCreacion());
    }
}