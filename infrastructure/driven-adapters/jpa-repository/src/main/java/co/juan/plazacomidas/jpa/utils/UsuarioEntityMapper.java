package co.juan.plazacomidas.jpa.utils;

import co.juan.plazacomidas.jpa.entities.UsuarioEntity;
import co.juan.plazacomidas.model.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

    @Mapping(source = "rol.idRol", target = "idRol")
    Usuario toDomain(UsuarioEntity entity);

    @Mapping(target = "rol", ignore = true)
    UsuarioEntity toEntity(Usuario domain);
}
