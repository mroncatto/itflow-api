package io.github.mroncatto.itflow.security.interfaces;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.mroncatto.itflow.security.model.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface IJwtService {

    String generateToken(UserPrincipal userPrincipal);
    String getSubject(String token);
    DecodedJWT decodedJWT(String token);
    Collection<SimpleGrantedAuthority> getAuthorities(String token);
    String[] getClaims(String token);


}
