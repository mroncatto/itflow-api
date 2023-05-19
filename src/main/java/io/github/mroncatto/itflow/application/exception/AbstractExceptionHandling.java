package io.github.mroncatto.itflow.application.exception;

import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;

public abstract class AbstractExceptionHandling {
    protected ResponseEntity<CustomHttpResponse> build(HttpStatus status, String message){
        return new ResponseEntity<>(CustomHttpResponse.builder()
                .status(status.value())
                .error(status.name())
                .message(message.toUpperCase())
                .timestamp(currentDate())
                .build(), status);
    }
}
