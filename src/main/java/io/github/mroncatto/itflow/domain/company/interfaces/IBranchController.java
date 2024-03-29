package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.company.model.Branch;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IBranchController extends IAbstractController<Branch> {
    ResponseEntity<Branch> findById(Long id) throws NoResultException;
    ResponseEntity<Branch> deleteById(Long id) throws NoResultException;
}
