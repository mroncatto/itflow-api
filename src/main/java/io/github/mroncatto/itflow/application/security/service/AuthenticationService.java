package io.github.mroncatto.itflow.application.security.service;

import io.github.mroncatto.itflow.application.security.jwt.JwtTokenDto;
import io.github.mroncatto.itflow.application.security.jwt.JwtTokenProvider;
import io.github.mroncatto.itflow.domain.user.model.IUserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final JwtTokenProvider tokenProvider;
    private final LoginAttemptService loginAttemptService;

    public JwtTokenDto login(String username, String password) {
            loginAttemptService.addUserToLoginAttemptCache(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            loginAttemptService.evictUserFromLoginAttemptCache(username);
            var user = userService.login(username);
            var token = tokenProvider.generate(UserDetailsImpl.build(user));
            return JwtTokenDto.builder()
                    .access_token(token)
                    .user(user)
                    .expire(tokenProvider.extractClaim(token, Claims::getExpiration))
                    .build();
    }
}
