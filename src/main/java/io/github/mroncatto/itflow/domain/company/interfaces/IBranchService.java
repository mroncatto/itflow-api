package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.company.model.Branch;

import javax.persistence.NoResultException;

public interface IBranchService extends IAbstractService<Branch> {
    Branch findById(Long id) throws NoResultException;
    Branch deleteById(Long id) throws NoResultException;
}
