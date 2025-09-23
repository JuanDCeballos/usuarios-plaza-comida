package co.juan.plazacomidas.api.utils;

import co.juan.plazacomidas.api.dto.UsuarioRequestDto;
import co.juan.plazacomidas.api.dto.UsuarioResponseDto;
import co.juan.plazacomidas.model.usuario.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioRequestDto requestDto);

    UsuarioResponseDto toUsuarioResponseDto(Usuario usuario);
}
