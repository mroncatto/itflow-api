package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IBranchController extends IAbstractController<Branch> {
    ResponseEntity<Branch> findById(Long id) throws NoResultException;
    ResponseEntity<Page<Branch>> findAll(int page, String filter);
    ResponseEntity<Branch> deleteById(Long id) throws NoResultException;
}
