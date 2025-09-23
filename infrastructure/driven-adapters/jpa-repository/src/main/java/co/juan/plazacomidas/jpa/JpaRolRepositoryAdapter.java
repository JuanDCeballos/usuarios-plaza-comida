package co.juan.plazacomidas.jpa;

import co.juan.plazacomidas.jpa.entities.RolEntity;
import co.juan.plazacomidas.jpa.helper.AdapterOperations;
import co.juan.plazacomidas.model.rol.Rol;
import co.juan.plazacomidas.model.rol.gateways.RolRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRolRepositoryAdapter extends AdapterOperations<Rol, RolEntity, Long, JpaRolRepository>
        implements RolRepository {

    public JpaRolRepositoryAdapter(JpaRolRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Rol.class));
    }

    @Override
    public boolean existePorId(Long idRol) {
        return repository.existsById(idRol);
    }
}
