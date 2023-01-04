package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.company.model.Department;

import javax.persistence.NoResultException;

public interface IDepartmentService extends IAbstractService<Department> {
    Department findById(Long id) throws NoResultException;
    Department deleteById(Long id) throws NoResultException;
}
