package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.company.interfaces.ICompanyService;
import io.github.mroncatto.itflow.domain.company.model.Company;
import io.github.mroncatto.itflow.domain.company.repository.ICompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
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
    public Company save(Company entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.companyRepository.save(entity);
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public Company update(Company entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Company company = this.findById(entity.getId());
        company.setName(entity.getName());
        company.setDocument(entity.getDocument());
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
