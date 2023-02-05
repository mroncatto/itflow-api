package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.company.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface ICompanyService extends IAbstractService<Company> {
    Company findById(Long id) throws NoResultException;
    Page<Company> findAll(Pageable pageable, String filter);
    Company deleteById(Long id) throws NoResultException;
}
