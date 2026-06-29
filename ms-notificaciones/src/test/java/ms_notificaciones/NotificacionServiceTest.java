package ms_notificaciones;

import ms_notificaciones.dto.NotificacionRequestDTO;
import ms_notificaciones.dto.NotificacionResponseDTO;
import ms_notificaciones.model.Notificacion;
import ms_notificaciones.repository.NotificacionRepository;
import ms_notificaciones.service.NotificacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificacionService - Pruebas Unitarias")
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    @DisplayName("enviarNotificacion: guarda correctamente la notificacion con estado ENVIADO")
    void enviarNotificacion_deberiaGuardarYRetornarEstadoEnviado() {
        
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setDestinatario("cliente@vetnova.cl");
        request.setTipo("EMAIL");
        request.setMensaje("Su mascota ya está lista para ser retirada.");

        
        Notificacion notificacionGuardada = new Notificacion(
                1L, 
                "cliente@vetnova.cl", 
                "EMAIL", 
                "Su mascota ya está lista para ser retirada.", 
                "ENVIADO", 
                LocalDateTime.now()
        );
        
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);

        
        NotificacionResponseDTO resultado = notificacionService.enviarNotificacion(request);

        
        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo("ENVIADO");
        assertThat(resultado.getDestinatario()).isEqualTo("cliente@vetnova.cl");
        
        
        verify(notificacionRepository).save(any(Notificacion.class));
    }
}