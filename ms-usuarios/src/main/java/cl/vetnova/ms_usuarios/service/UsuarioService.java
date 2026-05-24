package cl.vetnova.ms_usuarios.service;

import cl.vetnova.ms_usuarios.dto.UsuarioRequestDTO;
import cl.vetnova.ms_usuarios.dto.UsuarioResponseDTO;
import cl.vetnova.ms_usuarios.model.Usuario;
import cl.vetnova.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public List<UsuarioResponseDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {
        log.info("Registrando nuevo usuario con email: {}", dto.getEmail());
        
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        if (repository.findByRut(dto.getRut()).isPresent()) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // Considerar encriptación con BCrypt a futuro
        usuario.setRol(dto.getRol().toUpperCase());
        usuario.setActivo(true);
        
        return mapToDTO(repository.save(usuario));
    }
    
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        
        usuario.setNombre(dto.getNombre());
        usuario.setRol(dto.getRol().toUpperCase());
        // Se omiten RUT y Email para simplificar, en un escenario real se validarían duplicidades.
        
        log.info("Actualizando información de usuario id={}", id);
        return mapToDTO(repository.save(usuario));
    }

    public void deshabilitarUsuario(Long id) {
        log.info("Deshabilitando usuario id={}", id);
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        usuario.setActivo(false);
        repository.save(usuario);
    }

    private UsuarioResponseDTO mapToDTO(Usuario u) {
        return new UsuarioResponseDTO(u.getId(), u.getRut(), u.getNombre(), 
                u.getEmail(), u.getRol(), u.getActivo(), u.getFechaCreacion());
    }
}