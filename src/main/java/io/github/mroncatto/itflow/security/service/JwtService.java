package io.github.mroncatto.itflow.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.mroncatto.itflow.security.interfaces.IJwtService;
import io.github.mroncatto.itflow.security.model.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static io.github.mroncatto.itflow.config.constant.ApplicationConstant.APP_NAME;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.increaseDate;

@Component
@Slf4j
public class JwtService implements IJwtService {
    private final String secret;
    private final int expire;

    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expire}") int expire) {
        this.secret = secret;
        this.expire = expire;
    }

    @Override
    public String generateToken(UserPrincipal user) {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            token = JWT.create()
                    .withAudience(APP_NAME)
                    .withSubject(user.getUsername())
                    .withIssuedAt(currentDate())
                    .withExpiresAt(increaseDate(expire))
                    .withIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString())
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Failed to generate token: {}", e.getMessage());
        }
        return token;
    }

    @Override
    public String getSubject(String token) {
        return decodedJWT(token).getSubject();
    }

    @Override
    public DecodedJWT decodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaims(token);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(claims).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    @Override
    public String[] getClaims(String token) {
        return decodedJWT(token).getClaim("roles").asArray(String.class);
    }
}
