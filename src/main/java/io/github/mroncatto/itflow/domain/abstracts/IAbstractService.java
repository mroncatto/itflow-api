package io.github.mroncatto.itflow.domain.abstracts;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

public interface IAbstractService<U> {
    List<U> findAll();
    U save(U entity, BindingResult result) throws BadRequestException;
    U update(U entity, BindingResult result) throws BadRequestException, NoResultException;
}
