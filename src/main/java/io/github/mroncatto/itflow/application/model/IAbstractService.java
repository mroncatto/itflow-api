package io.github.mroncatto.itflow.application.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

public interface IAbstractService<T, U> {
    List<T> findAll();
    T save(U dto, BindingResult result) throws BadRequestException;
    T update(U dto, BindingResult result) throws BadRequestException, NoResultException;
}
