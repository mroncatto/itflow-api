package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

public interface IBranchService extends IAbstractService<Branch> {
    Branch findById(Long id) throws NoResultException;
    Page<Branch> findAll(Pageable pageable, String filter);
    Branch deleteById(Long id) throws NoResultException;
}
