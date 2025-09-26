package co.juan.plazacomidas.jpa;

import co.juan.plazacomidas.jpa.entities.RolEntity;
import co.juan.plazacomidas.jpa.helper.AdapterOperations;
import co.juan.plazacomidas.jpa.utils.RolEntityMapper;
import co.juan.plazacomidas.model.rol.Rol;
import co.juan.plazacomidas.model.rol.gateways.RolRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaRolRepositoryAdapter extends AdapterOperations<Rol, RolEntity, Long, JpaRolRepository>
        implements RolRepository {

    private final RolEntityMapper rolEntityMapper;

    public JpaRolRepositoryAdapter(JpaRolRepository repository, ObjectMapper mapper,
                                   RolEntityMapper rolEntityMapper) {
        super(repository, mapper, d -> mapper.map(d, Rol.class));
        this.rolEntityMapper = rolEntityMapper;
    }

    @Override
    public boolean existePorId(Long idRol) {
        return repository.existsById(idRol);
    }

    @Override
    public Optional<Rol> buscarPorNombre(String nombre) {
        return repository.findByNombre(nombre)
                .map(rolEntityMapper::toDomain);
    }
}
