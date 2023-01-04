package io.github.mroncatto.itflow.domain.abstracts;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

public interface IAbstractService<U> {
    List<U> findAll();
    Page<U> findAll(Pageable pageable);
    U save(U entity, BindingResult result) throws BadRequestException;
    U update(U entity, BindingResult result) throws BadRequestException, NoResultException;
}
