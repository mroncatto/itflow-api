package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.dto.CompanyDto;
import io.github.mroncatto.itflow.domain.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

public interface ICompanyService extends IAbstractService<Company, CompanyDto> {
    Company findById(Long id) throws NoResultException;
    Page<Company> findAll(Pageable pageable, String filter);
    Company deleteById(Long id) throws NoResultException;
}
