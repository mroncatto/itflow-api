package io.github.mroncatto.itflow.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.security.model.UserPrincipal;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.mroncatto.itflow.config.constant.ApplicationConstant.APP_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootConfiguration
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtServiceTest {

    static JwtService jwtService = new JwtService("123456", 10);
    static String token;
    static DecodedJWT decoded;

    @Order(1)
    @Test
    void shouldGenerateAToken() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().role("TEST").build());
        User account = User.builder()
                .username("integration_test")
                .role(roles)
                .build();
        UserPrincipal userPrincipal = new UserPrincipal(account);
        token = jwtService.generateToken(userPrincipal);
        Assert.isInstanceOf(String.class, token);
    }

    @Order(2)
    @Test
    void shouldDecodeToken() {
        decoded =  jwtService.decodedJWT(token);
        assertTrue(decoded.getAudience().iterator().next().equals(APP_NAME));
    }

    @Order(3)
    @Test
    void shouldReturnSubjectFromToken() {
        assertEquals("integration_test", jwtService.getSubject(token));
    }



    @Order(4)
    @Test
    void shouldReturnCredentials() {
        assertEquals("TEST", jwtService.getAuthorities(token).stream().iterator().next().getAuthority());
    }

    @Order(5)
    @Test
    void shouldReturnCredentialsClaims() {
        assertEquals("[TEST]", Arrays.toString(jwtService.getClaims(token)));
    }
}