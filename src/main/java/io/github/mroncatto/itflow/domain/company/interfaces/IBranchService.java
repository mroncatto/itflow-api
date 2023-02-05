package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.company.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IBranchService extends IAbstractService<Branch> {
    Branch findById(Long id) throws NoResultException;
    Page<Branch> findAll(Pageable pageable, String filter);
    Branch deleteById(Long id) throws NoResultException;
}
