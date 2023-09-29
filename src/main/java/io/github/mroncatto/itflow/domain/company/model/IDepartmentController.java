package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.NoResultException;

public interface IDepartmentController extends IAbstractController<Department> {
    ResponseEntity<Department> findById(Long id) throws NoResultException;
    ResponseEntity<Page<Department>> findAll(int page, String filter);
    ResponseEntity<Department> deleteById(Long id) throws NoResultException;
}
