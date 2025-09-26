package co.juan.plazacomidas.jpa.utils;

import co.juan.plazacomidas.jpa.entities.RolEntity;
import co.juan.plazacomidas.model.rol.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolEntityMapper {

    Rol toDomain(RolEntity entity);

    RolEntity toEntity(Rol rol);
}
