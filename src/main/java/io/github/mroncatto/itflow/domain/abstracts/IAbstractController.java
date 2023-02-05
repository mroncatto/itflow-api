package io.github.mroncatto.itflow.domain.abstracts;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

public interface IAbstractController<U> {
    ResponseEntity<List<U>> findAll();
    ResponseEntity<U> save(U entity, BindingResult result) throws BadRequestException;
    ResponseEntity<U> update(U entity, BindingResult result) throws BadRequestException, NoResultException;
}
