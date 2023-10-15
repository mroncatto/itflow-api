package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.dto.DepartmentRequestDto;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

public interface IDepartmentService extends IAbstractService<Department, DepartmentRequestDto> {
    Department findById(Long id) throws NoResultException;
    Page<Department> findAll(Pageable pageable, String filter);
    Department deleteById(Long id) throws NoResultException;
}
