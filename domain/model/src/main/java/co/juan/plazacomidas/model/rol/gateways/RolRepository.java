package co.juan.plazacomidas.model.rol.gateways;

import co.juan.plazacomidas.model.rol.Rol;

import java.util.Optional;

public interface RolRepository {

    boolean existePorId(Long idRol);

    Optional<Rol> buscarPorNombre(String nombre);
}
