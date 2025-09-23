package co.juan.plazacomidas.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        String secretKey = "VGhpcy1pcy1hLXZlcnktbG9uZy1hbmQtc2VjdXJlLXNlY3JldC1rZXktZm9yLXRlc3Rpbmc=";
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);

        userDetails = new User("test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

    @Test
    void cuandoGeneraToken_entoncesExtraeUsernameCorrectamente() {
        String token = jwtService.generateToken(userDetails);

        String extractedUsername = jwtService.extractUsername(token);

        assertNotNull(token);
        assertEquals("test@example.com", extractedUsername);
    }

    @Test
    void cuandoTokenEsValido_entoncesRetornaTrue() {
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }
}
