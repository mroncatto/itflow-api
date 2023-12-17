package io.github.mroncatto.itflow.application.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Slf4j
public abstract class AbstractService {

    protected void validateResult(BindingResult result) throws BadRequestException {
        if (result.hasErrors()) {
            throw new BadRequestException(getResultError(result));
        }
    }

    private String getResultError(BindingResult result) {
        String error = "";
        try {
            error = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
        } catch (Exception e) {
            log.error("Fail to get binding result field: {}", e.getMessage());
        }
        return error;
    }

    protected static Long convertToLong(Object value) {
        String data = value.toString().replaceAll("\\D", "");
        if (data.isEmpty() || data.isBlank()) data = "0";
        return Long.parseLong(data);
    }

}





















