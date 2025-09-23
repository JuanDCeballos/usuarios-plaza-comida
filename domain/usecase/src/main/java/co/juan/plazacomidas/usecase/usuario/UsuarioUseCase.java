package co.juan.plazacomidas.usecase.usuario;

import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.model.exceptions.ResourceNotFoundException;
import co.juan.plazacomidas.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public Usuario crearPropietario(Usuario usuario) {
        if (Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad.");
        }

        usuario.setIdRol(2L);

        return usuarioRepository.crearUsuario(usuario);
    }

    public Usuario crearEmpleado(Usuario usuario) {
        usuario.setIdRol(3L);

        return usuarioRepository.crearUsuario(usuario);
    }

    public Usuario obtenerById(Long idUsuario) {
        if (idUsuario == null || idUsuario.compareTo(1L) < 0) {
            throw new IllegalArgumentException("El id del usuario debe ser un número positivo.");
        }

        return usuarioRepository.obtenerById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el id: " + idUsuario));
    }
}
