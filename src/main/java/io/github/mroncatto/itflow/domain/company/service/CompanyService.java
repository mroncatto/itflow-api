package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.company.dto.CompanyDto;
import io.github.mroncatto.itflow.domain.company.model.ICompanyService;
import io.github.mroncatto.itflow.domain.company.entity.Company;
import io.github.mroncatto.itflow.infrastructure.persistence.ICompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService extends AbstractService implements ICompanyService {
    private final ICompanyRepository companyRepository;

    @Override
    public Company findById(Long id) throws NoResultException {
        return this.companyRepository.findById(id).orElseThrow(() -> new NoResultException("COMPANY NOT FOUND"));
    }

    @Override
    @Cacheable(value = "Company", key = "#root.method.name")
    public List<Company> findAll() {
        return this.companyRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Company> findAll(Pageable pageable, String filter) {
        return this.companyRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public Company save(CompanyDto dto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var company = new Company();
        BeanUtils.copyProperties(dto, company);
        return this.companyRepository.save(company);
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public Company update(CompanyDto dto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Company company = this.findById(dto.getId());
        company.setName(dto.getName());
        company.setDocument(dto.getDocument());
        return this.companyRepository.save(company);
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public Company deleteById(Long id) throws NoResultException {
        Company company = this.findById(id);
        company.setActive(false);
        return this.companyRepository.save(company);
    }

}
