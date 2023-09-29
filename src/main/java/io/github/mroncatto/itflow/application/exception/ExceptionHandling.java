package io.github.mroncatto.itflow.application.exception;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.domain.user.exception.UserNotFoundException;
import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.persistence.NoResultException;
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
public class ExceptionHandling extends AbstractExceptionHandling {

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<CustomHttpResponse> notFoundException(NoResultException exception) {
        log.error("No Result: {}", exception.getMessage());
        return build(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> usernameNotFoundException(UsernameNotFoundException exception){
        log.error("Username not found: {}", exception.getMessage());
        return build(HttpStatus.NOT_FOUND, !exception.getMessage().isEmpty() ? exception.getMessage() : USER_NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> userNotFoundException() {
        return build(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
    }

    @ExceptionHandler(AlreadExistingUserByUsername.class)
    public ResponseEntity<CustomHttpResponse> alreadExistingUserByUsername() {
        return build(HttpStatus.BAD_REQUEST, EXISTING_USER_BY_USERNAME);
    }

    @ExceptionHandler(AlreadExistingUserByEmail.class)
    public ResponseEntity<CustomHttpResponse> alreadExistingUserByEmail() {
        return build(HttpStatus.BAD_REQUEST, EXISTING_USER_BY_EMAIL);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomHttpResponse> badRequestException(BadRequestException exception) {
        return build(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<CustomHttpResponse> dataAccessException(DataAccessException exception) {
        //TODO: save log sql errors
        return build(HttpStatus.INTERNAL_SERVER_ERROR, DATABASE_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomHttpResponse> internalServerErrorException(Exception exception) {
        log.error("Error logging in {}", exception.getMessage());
        return build(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<CustomHttpResponse> credentialsExpiredException(CredentialsExpiredException exception) {
        return build(HttpStatus.BAD_REQUEST, EXPIRED_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomHttpResponse> accessDeniedException() {
        return build(HttpStatus.FORBIDDEN, ENOUGH_PRIVILEGES);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomHttpResponse> badCredentialsException() {
        return build(HttpStatus.UNAUTHORIZED, INVALID_PASSWORD);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<CustomHttpResponse> lockedException() {
        return build(HttpStatus.UNAUTHORIZED, "LOCK ACCOUNT, TRY LATER");
    }


    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<CustomHttpResponse> securityException(SecurityException e) {
        log.error("Invalid JWT signature: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "INVALID_JWT_SIGNATURE");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<CustomHttpResponse> malformedJwtException(MalformedJwtException e) {
        log.error("Invalid JWT token: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "INVALID_JWT_TOKEN");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CustomHttpResponse> expiredJwtException(ExpiredJwtException e) {
        log.error("JWT token is expired: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "JWT_TOKEN_EXPIRED");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<CustomHttpResponse> unsupportedJwtException(UnsupportedJwtException e) {
        log.error("JWT token is unsupported: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "JWT_TOKEN_UNSUPPORTED");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomHttpResponse> illegalArgumentException(IllegalArgumentException e) {
        log.error("JWT claims string is empty: {}", e.getMessage());
        return build(HttpStatus.UNAUTHORIZED, "JWT_CLAIMS_IS_EMPTY");
    }
}
