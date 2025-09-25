package co.juan.plazacomidas.usecase.usuario;

import co.juan.plazacomidas.model.exceptions.UsuarioYaRegistradoException;
import co.juan.plazacomidas.model.rol.Rol;
import co.juan.plazacomidas.model.rol.gateways.RolRepository;
import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.model.exceptions.ResourceNotFoundException;
import co.juan.plazacomidas.model.usuario.gateways.UsuarioRepository;
import co.juan.plazacomidas.model.utils.MensajesEnum;
import co.juan.plazacomidas.model.utils.RolEnum;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    private static final Integer EDAD_MINIMA = 18;

    public Usuario crearPropietario(Usuario usuario) {
        if (Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears() < EDAD_MINIMA) {
            throw new IllegalArgumentException(MensajesEnum.PROPIETARIO_MAYOR_EDAD.getMensaje());
        }

        return crearUsuarioConRol(usuario, RolEnum.PROPIETARIO);
    }

    public Usuario crearEmpleado(Usuario usuario) {
        return crearUsuarioConRol(usuario, RolEnum.EMPLEADO);
    }

    public Usuario crearCliente(Usuario usuario) {
        return crearUsuarioConRol(usuario, RolEnum.CLIENTE);
    }

    public Usuario obtenerById(Long idUsuario) {
        if (idUsuario == null || idUsuario.compareTo(1L) < 0) {
            throw new IllegalArgumentException(MensajesEnum.ID_POSITIVO.getMensaje());
        }

        return usuarioRepository.obtenerById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MensajesEnum.USUARIO_NO_ENCONTRADO_POR_ID.getMensaje() + idUsuario));
    }

    public Usuario obtenerByCorreo(String correo) {
        return usuarioRepository.obtenerByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MensajesEnum.USUARIO_NO_ENCONTRADO_POR_CORREO.getMensaje() + correo));
    }

    private Usuario crearUsuarioConRol(Usuario usuario, RolEnum rolEnum) {
        if (usuarioRepository.existePorCorreo(usuario.getCorreo())) {
            throw new UsuarioYaRegistradoException(MensajesEnum.CORREO_YA_REGISTRADO.getMensaje());
        }

        Rol rol = rolRepository.buscarPorNombre(rolEnum.getNombre())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MensajesEnum.ROL_NO_ENCONTRADO_POR_NOMBRE.getMensaje() + rolEnum.getNombre()));

        usuario.setIdRol(rol.getIdRol());

        return usuarioRepository.crearUsuario(usuario);
    }
}
