package io.github.mroncatto.itflow.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mroncatto.itflow.config.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.mroncatto.itflow.config.SecurityConstant.AUTHENTICATION_URL;
import static io.github.mroncatto.itflow.config.SecurityConstant.TOKEN_CANNOT_BE_VERIFIED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals(AUTHENTICATION_URL)){
            filterChain.doFilter(request, response);
        } else {
            //TODO Validar o token
            log.error("Error logging in: {}", "No autorizado");
            response.setHeader("Error", "No Authorized");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            Map<String, List<String>> error = new HashMap<>();
            List<String> errors = new ArrayList<>();
            errors.add(TOKEN_CANNOT_BE_VERIFIED);
            error.put("error", errors);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }
}
