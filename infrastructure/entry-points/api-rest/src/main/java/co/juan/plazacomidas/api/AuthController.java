package co.juan.plazacomidas.api;

import co.juan.plazacomidas.api.dto.ApiSuccessResponse;
import co.juan.plazacomidas.api.dto.UsuarioRequestDto;
import co.juan.plazacomidas.api.dto.UsuarioResponseDto;
import co.juan.plazacomidas.api.dto.auth.AuthRequestDto;
import co.juan.plazacomidas.api.dto.auth.AuthResponseDto;
import co.juan.plazacomidas.api.utils.UsuarioMapper;
import co.juan.plazacomidas.model.usuario.Usuario;
import co.juan.plazacomidas.security.JwtService;
import co.juan.plazacomidas.usecase.usuario.UsuarioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getClave())
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.getCorreo());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<UsuarioResponseDto>> register(@Valid @RequestBody UsuarioRequestDto requestDto) {
        Usuario usuario = usuarioMapper.toUsuario(requestDto);

        Usuario usuarioRegistrado = usuarioUseCase.crearCliente(usuario);

        UsuarioResponseDto responseDto = usuarioMapper.toUsuarioResponseDto(usuarioRegistrado);

        ApiSuccessResponse<UsuarioResponseDto> apiSuccessResponse = new ApiSuccessResponse<>(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }
}
