package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.company.dto.CompanyRequestDto;
import io.github.mroncatto.itflow.domain.company.entity.Company;
import io.github.mroncatto.itflow.domain.company.model.ICompanyService;
import io.github.mroncatto.itflow.infrastructure.persistence.ICompanyRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CompanyService extends AbstractService implements ICompanyService {
    private final ICompanyRepository companyRepository;
    private final MessageService messageService;

    @Override
    public Company findById(Long id) throws NoResultException {
        return this.companyRepository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("company")));
    }

    @Override
    @Cacheable(value = "Company", key = "#root.method.name")
    public List<Company> findAll() {
        return this.companyRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Company> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING COMPANY BY: {}", filter);
        return this.companyRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public Company save(CompanyRequestDto companyRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING COMPANY: {}", companyRequestDto.toString());
        return this.companyRepository.save(companyRequestDto.convert());
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public Company update(CompanyRequestDto companyRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Company company = this.findById(companyRequestDto.getId());
        company.setName(companyRequestDto.getName());
        company.setDocument(companyRequestDto.getDocument());
        log.debug(">>>UPDATING COMPANY: {}", companyRequestDto.toString());
        return this.companyRepository.save(company);
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public Company deleteById(Long id) throws NoResultException {
        Company company = this.findById(id);
        company.setActive(false);
        log.debug(">>>DELETING COMPANY BY: {}", id);
        return this.companyRepository.save(company);
    }

}
