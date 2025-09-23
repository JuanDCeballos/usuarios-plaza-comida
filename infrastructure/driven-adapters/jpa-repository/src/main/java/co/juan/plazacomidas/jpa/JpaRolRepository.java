package co.juan.plazacomidas.jpa;

import co.juan.plazacomidas.jpa.entities.RolEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface JpaRolRepository extends CrudRepository<RolEntity, Long>, QueryByExampleExecutor<RolEntity> {
}
