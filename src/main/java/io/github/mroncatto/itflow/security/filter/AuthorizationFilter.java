package io.github.mroncatto.itflow.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static io.github.mroncatto.itflow.config.constant.SecurityConstant.AUTHENTICATION_URL;
import static io.github.mroncatto.itflow.config.constant.SecurityConstant.TOKEN_CANNOT_BE_VERIFIED;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.notNull;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.startWith;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(AUTHENTICATION_URL)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (notNull(authorizationHeader) && startWith(authorizationHeader, "Bearer ")) {
                try {
                    SecurityContextHolder.getContext().setAuthentication(buildAuthorizationToken(authorizationHeader));
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    log.error("Error logging in: {}", e.getMessage());
                    new ObjectMapper().writeValue(responseUnauthorized(response).getOutputStream(),
                            CustomHttpResponse.builder()
                                    .timestamp(currentDate())
                                    .status(401)
                                    .error(TOKEN_CANNOT_BE_VERIFIED)
                                    .message("EXPIRED OR INVALID TOKEN, YOU MUST AUTHENTICATE AGAIN")
                                    .build());
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private UsernamePasswordAuthenticationToken buildAuthorizationToken(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        String username = this.jwtService.getSubject(token);
        Collection<SimpleGrantedAuthority> authorities = this.jwtService.getAuthorities(token);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private HttpServletResponse responseUnauthorized(HttpServletResponse response) {
        response.setHeader("ERROR", "UNAUTHORIZED");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        return response;
    }
}
