package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.company.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IDepartmentService extends IAbstractService<Department> {
    Department findById(Long id) throws NoResultException;
    Page<Department> findAll(Pageable pageable, String filter);
    Department deleteById(Long id) throws NoResultException;
}
