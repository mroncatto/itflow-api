package io.github.mroncatto.itflow.application.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

import static io.github.mroncatto.itflow.application.config.constant.ApplicationConstant.APP_NAME;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.increaseDate;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String secret;
    private final int expire;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expire}") int expire) {
        this.secret = secret;
        this.expire = expire;
    }

    public String generateToken(JwtUserDetails user) {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            token = JWT.create()
                    .withAudience(APP_NAME)
                    .withSubject(user.getUsername())
                    .withIssuedAt(currentDate())
                    .withExpiresAt(increaseDate(expire))
                    .withIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString())
                    .withClaim("credentials", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Failed to generate token: {}", e.getMessage());
        }
        return token;
    }

    public String getSubject(String token) {
        return decodedJWT(token).getSubject();
    }

    public DecodedJWT decodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public Collection<SimpleGrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaims(token);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(claims).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    public String[] getClaims(String token) {
        return decodedJWT(token).getClaim("credentials").asArray(String.class);
    }
}
