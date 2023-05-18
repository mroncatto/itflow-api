package io.github.mroncatto.itflow.application.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
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

    protected static void validateNullFields(Object... objects) throws BadRequestException {
        if (Arrays.stream(objects).anyMatch(Objects::isNull))
            throw new BadRequestException("ALL FIELDS ARE REQUIRED");
    }

    protected static void validateEmptyFields(String... fields) throws BadRequestException {
        if (Arrays.stream(fields).anyMatch(String::isEmpty))
            throw new BadRequestException("ALL FIELDS ARE REQUIRED");
    }

    protected static void validateEmailField(String email) throws BadRequestException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new BadRequestException("INVALID FORMAT EMAIL");
        }
    }

    protected static Long convertToLong(Object value) {
        String data = value.toString().replaceAll("[^0-9]", "");
        if (data.isEmpty() || data.isBlank()) data = "0";
        return Long.parseLong(data);
    }

}




















