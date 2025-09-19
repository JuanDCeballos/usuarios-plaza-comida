package co.juan.plazacomidas.jpa;

import co.juan.plazacomidas.jpa.entities.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface JpaUsuarioRepository extends CrudRepository<UsuarioEntity, Long>, QueryByExampleExecutor<UsuarioEntity> {
}
