package co.juan.plazacomidas.model.usuario.gateways;

import co.juan.plazacomidas.model.usuario.Usuario;

public interface UsuarioRepository {

    Usuario crearUsuario(Usuario usuario);

    Usuario obtenerById(Long idUsuario);
}
