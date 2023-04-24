package io.github.mroncatto.itflow.application.exception;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.infrastructure.web.controller.advice.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.domain.user.exception.BadPasswordException;
import io.github.mroncatto.itflow.domain.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling extends AbstractExceptionHandling {

    private static final String ENOUGH_PRIVILEGES = "ACCESS DENIED, YOU DON'T HAVE ENOUGH PRIVILEGES";
    private static final String USER_NOT_FOUND = "USER NOT FOUND";
    private static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    private static final String EXISTING_USER_BY_USERNAME = "USER ALREADY EXISTS WITH THIS USERNAME";
    private static final String EXISTING_USER_BY_EMAIL = "USER ALREADY EXISTS WITH THIS EMAIL";
    private static final String INVALID_PASSWORD = "INVALID PASSWORD";
    private static final String DATABASE_ERROR = "DATABASE ERROR";

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<CustomHttpResponse> notFoundException(NoResultException exception) {
        log.error("No Result: {}", exception.getMessage());
        return build(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> usernameNotFoundException(UsernameNotFoundException exception){
        log.error("Username not found: {}", exception.getMessage());
        return build(HttpStatus.NOT_FOUND, exception.getMessage().length() > 0 ? exception.getMessage() : USER_NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomHttpResponse> accessDeniedException() {
        return build(HttpStatus.FORBIDDEN, ENOUGH_PRIVILEGES);
    }

    @ExceptionHandler(BadPasswordException.class)
    public ResponseEntity<CustomHttpResponse> badPasswordException() {
        return build(HttpStatus.UNAUTHORIZED, INVALID_PASSWORD);
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
}
