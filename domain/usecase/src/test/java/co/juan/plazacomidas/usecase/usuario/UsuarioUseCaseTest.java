package co.juan.plazacomidas.usecase.usuario;

import co.juan.plazacomidas.model.exceptions.ResourceNotFoundException;
import co.juan.plazacomidas.model.exceptions.UsuarioYaRegistradoException;
import co.juan.plazacomidas.model.rol.Rol;
import co.juan.plazacomidas.model.rol.gateways.RolRepository;
import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.model.usuario.gateways.UsuarioRepository;
import co.juan.plazacomidas.model.utils.MensajesEnum;
import co.juan.plazacomidas.model.utils.RolEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @InjectMocks
    UsuarioUseCase usuarioUseCase;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    RolRepository rolRepository;

    private Usuario usuario;
    private Rol rol;

    private Long idUsuario = 1L;

    @BeforeEach
    void initMocks() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Ceballos");
        usuario.setDocumentoDeIdentidad(123876456L);
        usuario.setCelular("3274859483");
        usuario.setFechaNacimiento(LocalDate.of(2002, 9, 12));
        usuario.setCorreo("juan.juan@correo.com.co");
        usuario.setClave("123");
        usuario.setIdRol(1L);

        rol = new Rol();
        rol.setIdRol(1L);
        rol.setNombre("ADMINISTRADOR");
        rol.setDescripcion("Administrador del sistema");
    }

    @Test
    void crearPropietario() {
        when(usuarioRepository.crearUsuario(any(Usuario.class))).thenReturn(usuario);
        when(usuarioRepository.existePorCorreo(anyString())).thenReturn(false);
        when(rolRepository.buscarPorNombre(anyString())).thenReturn(Optional.of(rol));

        Usuario usuarioCreado = usuarioUseCase.crearPropietario(usuario);
        assertNotNull(usuarioCreado);
        assertEquals("Juan", usuarioCreado.getNombre());
        assertEquals(123876456L, usuarioCreado.getDocumentoDeIdentidad());

        verify(usuarioRepository, times(1)).crearUsuario(any(Usuario.class));
        verify(usuarioRepository, times(1)).existePorCorreo(anyString());
        verify(rolRepository, times(1)).buscarPorNombre(anyString());
    }

    @Test
    void crearPropietario_retornaException_usuarioMenorDeEdad() {
        usuario.setFechaNacimiento(LocalDate.of(2022, 9, 12));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> usuarioUseCase.crearPropietario(usuario));
        assertEquals(MensajesEnum.PROPIETARIO_MAYOR_EDAD.getMensaje(), exception.getMessage());

        verify(usuarioRepository, times(0)).crearUsuario(any(Usuario.class));
    }

    @Test
    void crearPropietario_retornaException_correoYaRegistrado() {
        when(usuarioRepository.existePorCorreo(anyString())).thenReturn(true);

        UsuarioYaRegistradoException exception = assertThrows(
                UsuarioYaRegistradoException.class, () -> usuarioUseCase.crearPropietario(usuario));
        assertEquals(MensajesEnum.CORREO_YA_REGISTRADO.getMensaje(), exception.getMessage());

        verify(usuarioRepository, times(0)).crearUsuario(any(Usuario.class));
        verify(usuarioRepository, times(1)).existePorCorreo(anyString());
        verify(rolRepository, times(0)).buscarPorNombre(anyString());
    }

    @Test
    void crearPropietario_retornaException_rolNoExiste() {
        when(usuarioRepository.existePorCorreo(anyString())).thenReturn(false);
        when(rolRepository.buscarPorNombre(anyString())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> usuarioUseCase.crearPropietario(usuario));
        assertEquals(MensajesEnum.ROL_NO_ENCONTRADO_POR_NOMBRE.getMensaje() +
                RolEnum.PROPIETARIO.getNombre(), exception.getMessage());

        verify(usuarioRepository, times(0)).crearUsuario(any(Usuario.class));
        verify(usuarioRepository, times(1)).existePorCorreo(anyString());
        verify(rolRepository, times(1)).buscarPorNombre(anyString());
    }

    @Test
    void obtenerById() {
        when(usuarioRepository.obtenerById(anyLong())).thenReturn(Optional.of(usuario));

        Usuario usuarioObtenido = usuarioUseCase.obtenerById(idUsuario);
        assertNotNull(usuarioObtenido);
        assertEquals("Juan", usuarioObtenido.getNombre());
        assertEquals(123876456L, usuarioObtenido.getDocumentoDeIdentidad());

        verify(usuarioRepository, times(1)).obtenerById(anyLong());
    }

    @Test
    void obtenerById_retornaException_parametroNull() {
        idUsuario = null;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> usuarioUseCase.obtenerById(idUsuario));
        assertEquals("El id del usuario debe ser un número positivo.", exception.getMessage());

        verify(usuarioRepository, times(0)).obtenerById(anyLong());
    }

    @Test
    void obtenerById_retornaException_parametroMenorAUno() {
        idUsuario = 0L;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> usuarioUseCase.obtenerById(idUsuario));
        assertEquals("El id del usuario debe ser un número positivo.", exception.getMessage());

        verify(usuarioRepository, times(0)).obtenerById(anyLong());
    }

    @Test
    void obtenerById_retornaException_usuarioNoExiste() {
        when(usuarioRepository.obtenerById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> usuarioUseCase.obtenerById(idUsuario));
        assertEquals(MensajesEnum.USUARIO_NO_ENCONTRADO_POR_ID.getMensaje() +
                idUsuario, exception.getMessage());

        verify(usuarioRepository, times(1)).obtenerById(anyLong());
    }

    @Test
    void crearEmpleado() {
        when(usuarioRepository.crearUsuario(any(Usuario.class))).thenReturn(usuario);
        when(usuarioRepository.existePorCorreo(anyString())).thenReturn(false);
        when(rolRepository.buscarPorNombre(anyString())).thenReturn(Optional.of(rol));

        Usuario usuarioCreado = usuarioUseCase.crearEmpleado(usuario);
        assertNotNull(usuarioCreado);
        assertEquals("Juan", usuarioCreado.getNombre());
        assertEquals(123876456L, usuarioCreado.getDocumentoDeIdentidad());

        verify(usuarioRepository, times(1)).crearUsuario(any(Usuario.class));
        verify(usuarioRepository, times(1)).existePorCorreo(anyString());
        verify(rolRepository, times(1)).buscarPorNombre(anyString());
    }

    @Test
    void crearEmpleado_retornaException_correoYaRegistrado() {
        when(usuarioRepository.existePorCorreo(anyString())).thenReturn(true);

        UsuarioYaRegistradoException exception = assertThrows(
                UsuarioYaRegistradoException.class, () -> usuarioUseCase.crearEmpleado(usuario));
        assertEquals(MensajesEnum.CORREO_YA_REGISTRADO.getMensaje(), exception.getMessage());

        verify(usuarioRepository, times(0)).crearUsuario(any(Usuario.class));
        verify(usuarioRepository, times(1)).existePorCorreo(anyString());
        verify(rolRepository, times(0)).buscarPorNombre(anyString());
    }

    @Test
    void obtenerByCorreo() {
        String correo = "juan.juan@correo.com.co";

        when(usuarioRepository.obtenerByCorreo(anyString())).thenReturn(Optional.of(usuario));

        Usuario usuarioObtenido = usuarioUseCase.obtenerByCorreo(correo);
        assertNotNull(usuarioObtenido);
        assertEquals("Juan", usuarioObtenido.getNombre());
        assertEquals(123876456L, usuarioObtenido.getDocumentoDeIdentidad());

        verify(usuarioRepository, times(1)).obtenerByCorreo(anyString());
    }

    @Test
    void obtenerByCorreo_retornaException_usuarioNoExistePorCorreo() {
        String correo = "juan.juan@correo.com.co";

        when(usuarioRepository.obtenerByCorreo(anyString())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> usuarioUseCase.obtenerByCorreo(correo));
        assertEquals(MensajesEnum.USUARIO_NO_ENCONTRADO_POR_CORREO.getMensaje() +
                correo, exception.getMessage());

        verify(usuarioRepository, times(1)).obtenerByCorreo(anyString());
    }

    @Test
    void crearCliente() {
        when(usuarioRepository.crearUsuario(any(Usuario.class))).thenReturn(usuario);
        when(usuarioRepository.existePorCorreo(anyString())).thenReturn(false);
        when(rolRepository.buscarPorNombre(anyString())).thenReturn(Optional.of(rol));

        Usuario usuarioCreado = usuarioUseCase.crearCliente(usuario);
        assertNotNull(usuarioCreado);
        assertEquals("Juan", usuarioCreado.getNombre());
        assertEquals(123876456L, usuarioCreado.getDocumentoDeIdentidad());

        verify(usuarioRepository, times(1)).crearUsuario(any(Usuario.class));
        verify(usuarioRepository, times(1)).existePorCorreo(anyString());
        verify(rolRepository, times(1)).buscarPorNombre(anyString());
    }

    @Test
    void crearCliente_retornaException_correoYaRegistrado() {
        when(usuarioRepository.existePorCorreo(anyString())).thenReturn(true);

        UsuarioYaRegistradoException exception = assertThrows(
                UsuarioYaRegistradoException.class, () -> usuarioUseCase.crearCliente(usuario));
        assertEquals(MensajesEnum.CORREO_YA_REGISTRADO.getMensaje(), exception.getMessage());

        verify(usuarioRepository, times(0)).crearUsuario(any(Usuario.class));
        verify(usuarioRepository, times(1)).existePorCorreo(anyString());
        verify(rolRepository, times(0)).buscarPorNombre(anyString());
    }
}
