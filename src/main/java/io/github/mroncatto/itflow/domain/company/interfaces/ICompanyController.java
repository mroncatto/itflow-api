package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.company.model.Company;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface ICompanyController extends IAbstractController<Company> {
    ResponseEntity<Company> findById(Long id) throws NoResultException;
    ResponseEntity<Company> deleteById(Long id) throws NoResultException;
}
