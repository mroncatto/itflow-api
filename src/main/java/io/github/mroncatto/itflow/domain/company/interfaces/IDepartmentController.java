package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.company.model.Department;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IDepartmentController extends IAbstractController<Department> {
    ResponseEntity<Department> findById(Long id) throws NoResultException;
    ResponseEntity<Department> deleteById(Long id) throws NoResultException;
}
