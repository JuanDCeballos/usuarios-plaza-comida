package co.juan.plazacomidas.security;

import co.juan.plazacomidas.jpa.JpaUsuarioRepository;
import co.juan.plazacomidas.jpa.entities.UsuarioEntity;
import co.juan.plazacomidas.model.utils.MensajesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JpaUsuarioRepository jpaUsuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        UsuarioEntity usuario = jpaUsuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(MensajesEnum.USUARIO_NO_ENCONTRADO_POR_CORREO.getMensaje() + correo));

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(usuario.getRol().getNombre())
        );

        return new User(
                usuario.getCorreo(),
                usuario.getClave(),
                authorities
        );
    }
}
