package io.github.mroncatto.itflow.config.exception;

import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.config.exception.model.UserNotFoundException;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;

import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    public static final String ENOUGH_PRIVILEGES = "ACCESS DENIED, YOU DON'T HAVE ENOUGH PRIVILEGES";
    public static final String USER_NOT_FOUND = "USER NOT FOUND";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    public static final String EXISTING_USER_BY_USERNAME = "ALREADY EXISTING USER BY USERNAME";
    public static final String EXISTING_USER_BY_EMAIL = "ALREADY EXISTING USER BY USERNAME";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomHttpResponse> internalServerErrorException(Exception exception) {
        log.error("Error logging in {}", exception.getMessage());
        return buildHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<CustomHttpResponse> notFoundException(NoResultException exception) {
        log.error("No Result: {}", exception.getMessage());
        return buildHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> usernameNotFoundException(UsernameNotFoundException exception){
        log.error("Username not found: {}", exception.getMessage());
        return buildHttpResponse(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomHttpResponse> accessDeniedException() {
        return buildHttpResponse(HttpStatus.FORBIDDEN, ENOUGH_PRIVILEGES);
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

    private ResponseEntity<CustomHttpResponse> buildHttpResponse(HttpStatus status, String message){
        return new ResponseEntity<>(CustomHttpResponse.builder()
                .status(status.value())
                .error(status.name())
                .message(message.toUpperCase())
                .timestamp(currentDate())
                .build(), UNAUTHORIZED);
    }
}
