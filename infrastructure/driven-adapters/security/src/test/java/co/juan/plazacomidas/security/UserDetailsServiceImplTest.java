package co.juan.plazacomidas.security;

import co.juan.plazacomidas.jpa.JpaUsuarioRepository;
import co.juan.plazacomidas.jpa.entities.RolEntity;
import co.juan.plazacomidas.jpa.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private JpaUsuarioRepository jpaUsuarioRepository;

    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void initMocks() {
        RolEntity rol = RolEntity.builder().idRol(1L).nombre("PROPIETARIO").build();

        usuarioEntity = UsuarioEntity.builder()
                .idUsuario(1L)
                .correo("test@example.com")
                .clave("hashedpassword")
                .rol(rol)
                .build();
    }

    @Test
    void cuandoUsuarioExiste_entoncesDevuelveUserDetails() {
        when(jpaUsuarioRepository.findByCorreo(anyString())).thenReturn(Optional.of(usuarioEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("hashedpassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("PROPIETARIO")));

        verify(jpaUsuarioRepository, times(1)).findByCorreo(anyString());
    }

    @Test
    void cuandoUsuarioNoExiste_entoncesLanzaUsernameNotFoundException() {
        when(jpaUsuarioRepository.findByCorreo(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("notfound@example.com");
        });

        verify(jpaUsuarioRepository, times(1)).findByCorreo(anyString());
    }
}
