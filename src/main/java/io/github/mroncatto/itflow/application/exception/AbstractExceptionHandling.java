package io.github.mroncatto.itflow.application.exception;

import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;

public abstract class   AbstractExceptionHandling {

    protected static final String ENOUGH_PRIVILEGES = "ACCESS DENIED, YOU DON'T HAVE ENOUGH PRIVILEGES";
    protected static final String INVALID_PASSWORD = "INVALID USER AND/OR PASSWORD";
    protected static final String USER_NOT_FOUND = "USER NOT FOUND";
    protected static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    protected static final String EXISTING_USER_BY_USERNAME = "USER ALREADY EXISTS WITH THIS USERNAME";
    protected static final String EXISTING_USER_BY_EMAIL = "USER ALREADY EXISTS WITH THIS EMAIL";
    protected static final String DATABASE_ERROR = "DATABASE ERROR";
    protected static final String EXPIRED_CREDENTIALS = "EXPIRED CREDENTIALS";

    protected ResponseEntity<CustomHttpResponse> build(HttpStatus status, String message){
        return new ResponseEntity<>(CustomHttpResponse.builder()
                .status(status.value())
                .error(status.name())
                .message(message.toUpperCase())
                .timestamp(currentDate())
                .build(), status);
    }
}
