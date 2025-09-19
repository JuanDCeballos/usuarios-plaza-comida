package co.juan.plazacomidas.model.usuario.gateways;

import co.juan.plazacomidas.model.usuario.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario crearUsuario(Usuario usuario);

    Optional<Usuario> obtenerById(Long idUsuario);
}
