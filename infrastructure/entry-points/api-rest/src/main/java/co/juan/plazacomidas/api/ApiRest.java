package co.juan.plazacomidas.api;

import co.juan.plazacomidas.api.dto.ApiErrorResponse;
import co.juan.plazacomidas.api.dto.ApiSuccessResponse;
import co.juan.plazacomidas.api.dto.UsuarioRequestDto;
import co.juan.plazacomidas.api.dto.UsuarioResponseDto;
import co.juan.plazacomidas.api.utils.UsuarioMapper;
import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.usecase.usuario.UsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @Operation(summary = "Crear un nuevo propietario",
            description = "Permite a un administrador crear un nuevo propietario de un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Petición invalida (ej. datos del DTO incorrectos)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/propietario")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ApiSuccessResponse<UsuarioResponseDto>> crearPropietario(@Valid @RequestBody UsuarioRequestDto requestDto) {
        Usuario usuario = usuarioMapper.toUsuario(requestDto);

        Usuario usuarioGuardado = usuarioUseCase.crearPropietario(usuario);

        UsuarioResponseDto responseDto = usuarioMapper.toUsuarioResponseDto(usuarioGuardado);

        ApiSuccessResponse<UsuarioResponseDto> apiSuccessResponse = new ApiSuccessResponse<>(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }

    @PostMapping("/empleado")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<ApiSuccessResponse<UsuarioResponseDto>> crearEmpleado(@Valid @RequestBody UsuarioRequestDto requestDto) {
        Usuario usuario = usuarioMapper.toUsuario(requestDto);

        Usuario usuarioGuardado = usuarioUseCase.crearEmpleado(usuario);

        UsuarioResponseDto responseDto = usuarioMapper.toUsuarioResponseDto(usuarioGuardado);

        ApiSuccessResponse<UsuarioResponseDto> apiSuccessResponse = new ApiSuccessResponse<>(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiSuccessResponse<UsuarioResponseDto>> obtenerById(@PathVariable("idUsuario") Long idUsuario) {
        Usuario usuario = usuarioUseCase.obtenerById(idUsuario);

        UsuarioResponseDto responseDto = usuarioMapper.toUsuarioResponseDto(usuario);

        ApiSuccessResponse<UsuarioResponseDto> apiSuccessResponse = new ApiSuccessResponse<>(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<ApiSuccessResponse<UsuarioResponseDto>> obtenerByCorreo(@PathVariable("correo") String correo) {
        Usuario usuario = usuarioUseCase.obtenerByCorreo(correo);

        UsuarioResponseDto responseDto = usuarioMapper.toUsuarioResponseDto(usuario);

        ApiSuccessResponse<UsuarioResponseDto> apiSuccessResponse = new ApiSuccessResponse<>(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }
}
