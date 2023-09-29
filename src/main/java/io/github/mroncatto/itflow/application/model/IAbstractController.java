package io.github.mroncatto.itflow.application.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import jakarta.persistence.NoResultException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IAbstractController<U> {
    ResponseEntity<List<U>> findAll();
    ResponseEntity<U> save(U entity, BindingResult result) throws BadRequestException;
    ResponseEntity<U> update(U entity, BindingResult result) throws BadRequestException, NoResultException;
}
