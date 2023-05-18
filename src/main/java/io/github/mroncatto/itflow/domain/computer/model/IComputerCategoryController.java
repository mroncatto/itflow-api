package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IComputerCategoryController extends IAbstractController<ComputerCategory> {
    ResponseEntity<ComputerCategory> findById(Long id) throws NoResultException;
    ResponseEntity<Page<ComputerCategory>> findAll(int page, String filter);
    ResponseEntity<ComputerCategory> deleteById(Long id) throws NoResultException;
}