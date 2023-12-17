package io.github.mroncatto.itflow.application.jwt;

import io.github.mroncatto.itflow.application.security.jwt.JwtTokenProvider;
import io.github.mroncatto.itflow.application.security.jwt.JwtUserDetails;
import io.github.mroncatto.itflow.domain.user.entity.UserRole;
import io.github.mroncatto.itflow.domain.user.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtTokenProviderTest {

    static JwtTokenProvider jwtService = new JwtTokenProvider("123456", 10);
    static String token;

    @Order(1)
    @Test
    void shouldGenerateAToken() {
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.builder().role("TEST").build());
        User account = User.builder()
                .username("integration_test")
                .role(roles)
                .build();
        JwtUserDetails userPrincipal = new JwtUserDetails(account);
        //token = jwtService.generateToken(userPrincipal);
        //Assert.isInstanceOf(String.class, token);
    }

    @Order(2)
    @Test
    void shouldDecodeToken() {
        //decoded =  jwtService.decodedJWT(token);
        //assertTrue(decoded.getAudience().iterator().next().equals(APP_NAME));
    }

    @Order(3)
    @Test
    void shouldReturnSubjectFromToken() {
        //assertEquals("integration_test", jwtService.getSubject(token));
    }



    @Order(4)
    @Test
    void shouldReturnCredentials() {
        //assertEquals("TEST", jwtService.getAuthorities(token).stream().iterator().next().getAuthority());
    }

    @Order(5)
    @Test
    void shouldReturnCredentialsClaims() {
        //assertEquals("[TEST]", Arrays.toString(jwtService.getClaims(token)));
    }
}