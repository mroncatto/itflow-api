package io.github.mroncatto.itflow.config.exception;

import io.github.mroncatto.itflow.config.exception.model.*;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.persistence.NoResultException;

import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    public static final String ENOUGH_PRIVILEGES = "ACCESS DENIED, YOU DON'T HAVE ENOUGH PRIVILEGES";
    public static final String USER_NOT_FOUND = "USER NOT FOUND";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    public static final String EXISTING_USER_BY_USERNAME = "USER ALREADY EXISTS WITH THIS USERNAME";
    public static final String EXISTING_USER_BY_EMAIL = "USER ALREADY EXISTS WITH THIS EMAIL";
    public static final String INVALID_PASSWORD = "INVALID PASSWORD";
    public static final String DATABASE_ERROR = "DATABASE ERROR";
    public static final String UNSUCCESSFUL_AUTHENTICATION = "UNSUCCESSFUL AUTHENTICATION";

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<CustomHttpResponse> notFoundException(NoResultException exception) {
        log.error("No Result: {}", exception.getMessage());
        return buildHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> usernameNotFoundException(UsernameNotFoundException exception){
        log.error("Username not found: {}", exception.getMessage());
        return buildHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage().length() > 0 ? exception.getMessage() : USER_NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomHttpResponse> accessDeniedException() {
        return buildHttpResponse(HttpStatus.FORBIDDEN, ENOUGH_PRIVILEGES);
    }

    @ExceptionHandler(BadPasswordException.class)
    public ResponseEntity<CustomHttpResponse> badPasswordException() {
        return buildHttpResponse(HttpStatus.UNAUTHORIZED, INVALID_PASSWORD);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> userNotFoundException() {
        return buildHttpResponse(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
    }

    @ExceptionHandler(AlreadExistingUserByUsername.class)
    public ResponseEntity<CustomHttpResponse> alreadExistingUserByUsername() {
        return buildHttpResponse(HttpStatus.BAD_REQUEST, EXISTING_USER_BY_USERNAME);
    }

    @ExceptionHandler(AlreadExistingUserByEmail.class)
    public ResponseEntity<CustomHttpResponse> alreadExistingUserByEmail() {
        return buildHttpResponse(HttpStatus.BAD_REQUEST, EXISTING_USER_BY_EMAIL);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomHttpResponse> badRequestException(BadRequestException exception) {
        return buildHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<CustomHttpResponse> dataAccessException(DataAccessException exception) {
        //TODO: save log sql errors
        return buildHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, DATABASE_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomHttpResponse> internalServerErrorException(Exception exception) {
        log.error("Error logging in {}", exception.getMessage());
        return buildHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomHttpResponse> authenticationException(Exception exception) {
        return buildHttpResponse(HttpStatus.UNAUTHORIZED, UNSUCCESSFUL_AUTHENTICATION);
    }

    private ResponseEntity<CustomHttpResponse> buildHttpResponse(HttpStatus status, String message){
        return new ResponseEntity<>(CustomHttpResponse.builder()
                .status(status.value())
                .error(status.name())
                .message(message.toUpperCase())
                .timestamp(currentDate())
                .build(), status);
    }
}
