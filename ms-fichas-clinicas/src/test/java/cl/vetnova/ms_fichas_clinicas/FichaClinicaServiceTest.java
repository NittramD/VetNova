package cl.vetnova.ms_fichas_clinicas;

import cl.vetnova.ms_fichas_clinicas.dto.FichaRequestDTO;
import cl.vetnova.ms_fichas_clinicas.dto.FichaResponseDTO;
import cl.vetnova.ms_fichas_clinicas.model.FichaClinica;
import cl.vetnova.ms_fichas_clinicas.repository.FichaClinicaRepository;
import cl.vetnova.ms_fichas_clinicas.service.FichaClinicaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FichaClinicaService - Pruebas Unitarias")
class FichaClinicaServiceTest {

    @Mock
    private FichaClinicaRepository fichaClinicaRepository;

    @InjectMocks
    private FichaClinicaService fichaClinicaService;

    @Test
    @DisplayName("registrarFicha: guarda correctamente una nueva ficha clinica")
    void registrarFicha_deberiaGuardarYRetornarFicha() {
        
        
        FichaRequestDTO request = new FichaRequestDTO();
        request.setMascotaId(100L);
        request.setCitaMedicaId(50L);
        request.setVeterinarioId(10L);
        request.setDiagnostico("Paciente sano");
        request.setTratamiento("Ninguno");
        request.setReceta("Vitaminas");

        
        FichaClinica fichaGuardada = new FichaClinica();
        fichaGuardada.setId(1L);
        fichaGuardada.setMascotaId(100L);
        fichaGuardada.setCitaMedicaId(50L);
        fichaGuardada.setVeterinarioId(10L);
        fichaGuardada.setDiagnostico("Paciente sano");
        fichaGuardada.setTratamiento("Ninguno");
        fichaGuardada.setReceta("Vitaminas");
        
        when(fichaClinicaRepository.save(any(FichaClinica.class))).thenReturn(fichaGuardada);

        FichaResponseDTO resultado = fichaClinicaService.registrarFicha(request);

        // 4. Validamos que funcione
        assertThat(resultado).isNotNull();
        assertThat(resultado.getMascotaId()).isEqualTo(100L);
        assertThat(resultado.getDiagnostico()).isEqualTo("Paciente sano");
        verify(fichaClinicaRepository).save(any(FichaClinica.class));
    }
}