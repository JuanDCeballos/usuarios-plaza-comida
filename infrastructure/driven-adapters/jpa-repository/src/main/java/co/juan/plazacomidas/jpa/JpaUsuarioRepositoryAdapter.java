package co.juan.plazacomidas.jpa;

import co.juan.plazacomidas.jpa.entities.UsuarioEntity;
import co.juan.plazacomidas.jpa.helper.AdapterOperations;
import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.model.usuario.gateways.UsuarioRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUsuarioRepositoryAdapter extends AdapterOperations<Usuario, UsuarioEntity, Long, JpaUsuarioRepository>
        implements UsuarioRepository {

    public JpaUsuarioRepositoryAdapter(JpaUsuarioRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Usuario.class));
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return save(usuario);
    }

    @Override
    public Usuario obtenerById(Long idUsuario) {
        return findById(idUsuario);
    }
}
