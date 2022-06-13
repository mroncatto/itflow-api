package io.github.mroncatto.itflow.domain.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IEntityController<T> {
    ResponseEntity<List<T>> findAll();
    ResponseEntity<Page<T>> findAll(int page);
    ResponseEntity<T> save(T entity, BindingResult result) throws BadRequestException;
}
