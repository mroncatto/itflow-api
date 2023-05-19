package io.github.mroncatto.itflow.application.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.application.security.jwt.JwtTokenProvider;
import io.github.mroncatto.itflow.application.security.jwt.JwtUserDetails;
import io.github.mroncatto.itflow.domain.commons.helper.DateHelper;
import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.user.entity.User;
import io.github.mroncatto.itflow.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtService;
    private final LoginAttemptService loginAttemptService;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getParameter("username"),
                request.getParameter("password")
        );
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUserDetails user = (JwtUserDetails) authResult.getPrincipal();
        User account = this.userService.login(user.getUsername());
        loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        final String token = this.jwtService.generateToken(user);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), buildToken(token, account));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String username = request.getParameter("username");
        if(nonNull(username)) loginAttemptService.addUserToLoginAttemptCache(username);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(401);
        new ObjectMapper().writeValue(response.getOutputStream(), buildUnauthorizedResponse(failed));
    }

    private Map<String, Object> buildToken(String token, User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("access_token", token);
        response.put("expire", jwtService.decodedJWT(token).getExpiresAt());
        response.put("user", user.buildForToken());
        return response;
    }

    private CustomHttpResponse buildUnauthorizedResponse(AuthenticationException failed){
        return CustomHttpResponse.builder()
                .timestamp(DateHelper.currentDate())
                .status(401)
                .error("UNSUCCESSFUL AUTHENTICATION")
                .message(failed.getLocalizedMessage().toUpperCase())
                .build();
    }
}
