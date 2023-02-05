package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.company.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IDepartmentController extends IAbstractController<Department> {
    ResponseEntity<Department> findById(Long id) throws NoResultException;
    ResponseEntity<Page<Department>> findAll(int page, String filter);
    ResponseEntity<Department> deleteById(Long id) throws NoResultException;
}
