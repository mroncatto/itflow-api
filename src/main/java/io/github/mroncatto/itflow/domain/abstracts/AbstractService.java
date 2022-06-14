package io.github.mroncatto.itflow.domain.abstracts;

import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractService<U> {

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
    
    public abstract List<U> findAll();
    public abstract Page<U> findAll(Pageable pageable);
    public abstract U save(U entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    public abstract U update(String username, U entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    public abstract void delete(String username);
}
