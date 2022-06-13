package io.github.mroncatto.itflow.domain.abstracts;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Slf4j
public class AbstractService {

    protected void validateResult(BindingResult result) throws BadRequestException {
        if(result.hasErrors()){
            throw new BadRequestException(getResultError(result));
        }
    }

    private String getResultError(BindingResult result) {
        String error = "";
        try {
            error = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
        } catch (Exception e){
            log.error("Fail to get binding result field: {}", e.getMessage());
        }
        return error;
    }
}
