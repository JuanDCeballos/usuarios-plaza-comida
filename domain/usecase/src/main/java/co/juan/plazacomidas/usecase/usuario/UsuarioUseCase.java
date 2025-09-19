package co.juan.plazacomidas.usecase.usuario;

import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.model.usuario.exceptions.ResourceNotFoundException;
import co.juan.plazacomidas.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        if (Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears() < 18) {
            //TODO: CAMBIAR EL MENSAJE "QUEMADO" POR UN ENUM
            throw new IllegalArgumentException("El usuario debe ser mayor de edad.");
        }

        return usuarioRepository.crearUsuario(usuario);
    }

    public Usuario obtenerById(Long idUsuario) {
        if (idUsuario == null || idUsuario.compareTo(1L) < 0) {
            throw new IllegalArgumentException("El id del usuario debe ser mayor a 0");
        }

        Usuario usuario = usuarioRepository.obtenerById(idUsuario);

        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado con el id: " + idUsuario);
        }

        return usuario;
    }
}
