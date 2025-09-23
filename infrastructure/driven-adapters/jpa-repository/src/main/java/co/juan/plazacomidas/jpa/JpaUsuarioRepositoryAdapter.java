package co.juan.plazacomidas.jpa;

import co.juan.plazacomidas.jpa.entities.RolEntity;
import co.juan.plazacomidas.jpa.entities.UsuarioEntity;
import co.juan.plazacomidas.jpa.helper.AdapterOperations;
import co.juan.plazacomidas.jpa.utils.UsuarioEntityMapper;
import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.model.exceptions.ResourceNotFoundException;
import co.juan.plazacomidas.model.usuario.gateways.UsuarioRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUsuarioRepositoryAdapter extends AdapterOperations<Usuario, UsuarioEntity, Long, JpaUsuarioRepository>
        implements UsuarioRepository {

    private final JpaRolRepository jpaRolRepository;
    private final UsuarioEntityMapper usuarioEntityMapper;
    private final PasswordEncoder passwordEncoder;

    public JpaUsuarioRepositoryAdapter(JpaUsuarioRepository repository, ObjectMapper mapper,
                                       JpaRolRepository jpaRolRepository, UsuarioEntityMapper usuarioEntityMapper,
                                       PasswordEncoder passwordEncoder) {
        super(repository, mapper, d -> mapper.map(d, Usuario.class));
        this.jpaRolRepository = jpaRolRepository;
        this.usuarioEntityMapper = usuarioEntityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioEntityMapper.toEntity(usuario);

        RolEntity rolEntity = jpaRolRepository.findById(usuario.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado en la base de datos con id: " + usuario.getIdRol()));

        usuarioEntity.setRol(rolEntity);
        usuarioEntity.setClave(passwordEncoder.encode(usuario.getClave()));

        UsuarioEntity usuarioGuardado = repository.save(usuarioEntity);

        return usuarioEntityMapper.toDomain(usuarioGuardado);
    }

    @Override
    public Optional<Usuario> obtenerById(Long idUsuario) {
        return repository.findById(idUsuario)
                .map(usuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> obtenerByCorreo(String correo) {
        return repository.findByCorreo(correo)
                .map(usuarioEntityMapper::toDomain);
    }
}
