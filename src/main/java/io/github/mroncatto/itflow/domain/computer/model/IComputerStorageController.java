package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IComputerStorageController extends IAbstractController<ComputerStorage> {
    ResponseEntity<ComputerStorage> findById(Long id) throws NoResultException;
    ResponseEntity<Page<ComputerStorage>> findAll(int page, String filter);
    ResponseEntity<ComputerStorage> deleteById(Long id) throws NoResultException;
}
