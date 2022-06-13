package io.github.mroncatto.itflow.domain.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.config.exception.model.NotFoundException;
import io.github.mroncatto.itflow.config.exception.model.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IEntityService<T> {
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    T findUserById(Long id) throws UserNotFoundException;
    T save(T entity, BindingResult result) throws BadRequestException;
}
