package cl.vetnova.ms_mascotas;

import cl.vetnova.ms_mascotas.dto.MascotaRequestDTO;
import cl.vetnova.ms_mascotas.dto.MascotaResponseDTO;
import cl.vetnova.ms_mascotas.model.Mascota;
import cl.vetnova.ms_mascotas.repository.MascotaRepository;
import cl.vetnova.ms_mascotas.service.MascotaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @Mock
    private MascotaRepository repository;

    @InjectMocks
    private MascotaService mascotaService;

    @Test
    void obtenerTodas_deberiaRetornarLista() {
        
        Mascota mascota = new Mascota(1L, "Rex", "Perro", "Labrador", LocalDate.of(2020, 5, 10), 100L);
        when(repository.findAll()).thenReturn(List.of(mascota));

        
        List<MascotaResponseDTO> resultado = mascotaService.obtenerTodas();

        
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Rex");
        verify(repository).findAll();
    }

    @Test
    void registrarMascota_deberiaGuardarMascota() {
        
        MascotaRequestDTO request = new MascotaRequestDTO();
        request.setNombre("Luna");
        request.setEspecie("Gato");
        request.setRaza("Siamés");
        request.setFechaNacimiento(LocalDate.of(2021, 8, 15));
        request.setClienteId(200L);

        Mascota mascotaGuardada = new Mascota(1L, "Luna", "Gato", "Siamés", LocalDate.of(2021, 8, 15), 200L);
        when(repository.save(any(Mascota.class))).thenReturn(mascotaGuardada);

        
        MascotaResponseDTO resultado = mascotaService.registrarMascota(request);

        
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Luna");
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(repository).save(any(Mascota.class));
    }

    @Test
    void actualizarMascota_mascotaNoExiste_lanzaExcepcion() {
        
        Long idInexistente = 99L;
        MascotaRequestDTO request = new MascotaRequestDTO();
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> mascotaService.actualizarMascota(idInexistente, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Mascota no encontrada con id: 99");
    }
}