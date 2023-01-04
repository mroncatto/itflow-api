package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.company.model.Company;

import javax.persistence.NoResultException;

public interface ICompanyService extends IAbstractService<Company> {
    Company findById(Long id) throws NoResultException;
    Company deleteById(Long id) throws NoResultException;
}
