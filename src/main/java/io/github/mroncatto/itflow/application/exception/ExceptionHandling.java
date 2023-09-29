package io.github.mroncatto.itflow.application.exception;

import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.domain.user.exception.UserNotFoundException;
import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandling extends AbstractExceptionHandling {
    private final MessageService messageService;

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<CustomHttpResponse> notFoundException(NoResultException exception) {
        log.error("No Result: {}", exception.getMessage());
        return build(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> usernameNotFoundException(UsernameNotFoundException exception){
        log.error("Username not found: {}", exception.getMessage());
        return build(HttpStatus.NOT_FOUND, !exception.getMessage().isEmpty() ? exception.getMessage() : messageService.getMessage("exception.user_not_found"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> userNotFoundException() {
        log.error("User not found");
        return build(HttpStatus.NOT_FOUND, messageService.getMessage("exception.user_not_found"));
    }

    @ExceptionHandler(AlreadExistingUserByUsername.class)
    public ResponseEntity<CustomHttpResponse> alreadExistingUserByUsername() {
        log.error("Username already exists");
        return build(HttpStatus.BAD_REQUEST, messageService.getMessage("exception.username_already_exists"));
    }

    @ExceptionHandler(AlreadExistingUserByEmail.class)
    public ResponseEntity<CustomHttpResponse> alreadExistingUserByEmail() {
        log.error("Email already exists");
        return build(HttpStatus.BAD_REQUEST, messageService.getMessage("exception.email_already_exists"));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomHttpResponse> badRequestException(BadRequestException exception) {
        log.error("Bad request: {}", exception.getMessage());
        return build(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<CustomHttpResponse> dataAccessException(DataAccessException exception) {
        log.error("Data Access error: {}", exception.getMessage());
        return build(HttpStatus.INTERNAL_SERVER_ERROR, messageService.getMessage("exception.database_error"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomHttpResponse> internalServerErrorException(Exception exception) {
        log.error("Generic error: {}", exception.getMessage());
        return build(HttpStatus.INTERNAL_SERVER_ERROR, messageService.getMessage("exception.internal_server_error"));
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<CustomHttpResponse> credentialsExpiredException(CredentialsExpiredException exception) {
        log.error("Credentials Expired: {}", exception.getMessage());
        return build(HttpStatus.BAD_REQUEST, messageService.getMessage("exception.expired_credentials"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomHttpResponse> accessDeniedException() {
        log.error("Access denied, no privileges enough");
        return build(HttpStatus.FORBIDDEN, messageService.getMessage("exception.not_privileges"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomHttpResponse> badCredentialsException() {
        log.error("Invalid Credentials");
        return build(HttpStatus.UNAUTHORIZED, messageService.getMessage("exception.bad_credentials"));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<CustomHttpResponse> lockedException() {
        log.error("User account locked");
        return build(HttpStatus.UNAUTHORIZED, messageService.getMessage("exception.locked"));
    }


    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<CustomHttpResponse> securityException(SecurityException e) {
        log.error("Invalid JWT signature: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, messageService.getMessage("exception.invalid_jwt_signature"));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<CustomHttpResponse> malformedJwtException(MalformedJwtException e) {
        log.error("Invalid JWT token: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, messageService.getMessage("exception.invalid_jwt_token"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CustomHttpResponse> expiredJwtException(ExpiredJwtException e) {
        log.error("JWT token is expired: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, messageService.getMessage("exception.expired_jwt_token"));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<CustomHttpResponse> unsupportedJwtException(UnsupportedJwtException e) {
        log.error("JWT token is unsupported: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, messageService.getMessage("exception.unsupported_jwt_token"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomHttpResponse> illegalArgumentException(IllegalArgumentException e) {
        log.error("JWT claims string is empty: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, messageService.getMessage("exception.illegal_arg_jwt_token"));
    }
}
