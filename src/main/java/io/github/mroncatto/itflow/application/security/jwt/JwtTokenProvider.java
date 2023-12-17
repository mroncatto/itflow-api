package io.github.mroncatto.itflow.application.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import static io.github.mroncatto.itflow.application.config.constant.ApplicationConstant.APP_NAME;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.increaseDate;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String jwtSecret;
    private final int jwtExpire;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expire}") int expire) {
        this.jwtSecret = secret;
        this.jwtExpire = expire;
    }
    public String generate(UserDetails userDetails) {
        return Jwts.builder()
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .setAudience(APP_NAME)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(currentDate())
                .setExpiration(increaseDate(jwtExpire))
                .setIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
