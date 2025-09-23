package co.juan.plazacomidas.usecase.usuario;

import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.model.usuario.gateways.UsuarioRepository;
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

    private Usuario usuario;
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
    }

    @Test
    void crearPropietario() {
        when(usuarioRepository.crearUsuario(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioCreado = usuarioUseCase.crearPropietario(usuario);
        assertNotNull(usuarioCreado);
        assertEquals("Juan", usuarioCreado.getNombre());
        assertEquals(123876456L, usuarioCreado.getDocumentoDeIdentidad());

        verify(usuarioRepository, times(1)).crearUsuario(any(Usuario.class));
    }

    @Test
    void crearPropietario_retornaException_usuarioMenorDeEdad() {
        usuario.setFechaNacimiento(LocalDate.of(2022, 9, 12));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioUseCase.crearPropietario(usuario);
        });
        assertEquals("El usuario debe ser mayor de edad.", exception.getMessage());

        verify(usuarioRepository, times(0)).crearUsuario(any(Usuario.class));
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

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioUseCase.obtenerById(idUsuario);
        });
        assertEquals("El id del usuario debe ser un número positivo.", exception.getMessage());

        verify(usuarioRepository, times(0)).obtenerById(anyLong());
    }

    @Test
    void obtenerById_retornaException_parametroMenorAUno() {
        idUsuario = 0L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioUseCase.obtenerById(idUsuario);
        });
        assertEquals("El id del usuario debe ser un número positivo.", exception.getMessage());

        verify(usuarioRepository, times(0)).obtenerById(anyLong());
    }

    @Test
    void crearEmpleado() {
        when(usuarioRepository.crearUsuario(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioCreado = usuarioUseCase.crearEmpleado(usuario);
        assertNotNull(usuarioCreado);
        assertEquals("Juan", usuarioCreado.getNombre());
        assertEquals(123876456L, usuarioCreado.getDocumentoDeIdentidad());

        verify(usuarioRepository, times(1)).crearUsuario(any(Usuario.class));
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
}
