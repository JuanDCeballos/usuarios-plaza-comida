package co.juan.plazacomidas.api;

import co.juan.plazacomidas.api.dto.ApiResponse;
import co.juan.plazacomidas.api.dto.UsuarioRequestDto;
import co.juan.plazacomidas.api.dto.UsuarioResponseDto;
import co.juan.plazacomidas.api.utils.UsuarioMapper;
import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.usecase.usuario.UsuarioUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> crearUsuario(@Valid @RequestBody UsuarioRequestDto requestDto) {
        Usuario usuario = usuarioMapper.toUsuario(requestDto);

        Usuario usuarioGuardado = usuarioUseCase.crearUsuario(usuario);

        UsuarioResponseDto responseDto = usuarioMapper.toUsuarioResponseDto(usuarioGuardado);

        ApiResponse<UsuarioResponseDto> apiResponse = new ApiResponse<>(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> obtenerById(@PathVariable("idUsuario") Long idUsuario){
        Usuario usuario = usuarioUseCase.obtenerById(idUsuario);

        UsuarioResponseDto responseDto = usuarioMapper.toUsuarioResponseDto(usuario);

        ApiResponse<UsuarioResponseDto> apiResponse = new ApiResponse<>(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
