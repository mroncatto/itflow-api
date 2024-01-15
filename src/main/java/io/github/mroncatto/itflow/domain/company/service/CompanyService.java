package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.company.dto.CompanyRequestDto;
import io.github.mroncatto.itflow.domain.company.dto.CompanyResponseDto;
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
    public CompanyResponseDto findById(Long id) throws NoResultException {
        return findCompanyById(id).response();
    }

    @Override
    @Cacheable(value = "Company", key = "#root.method.name")
    public List<CompanyResponseDto> findAll() {
        var company = this.companyRepository.findAllByActiveTrue();
        return company.stream().map(Company::response).toList();
    }

    @Override
    public Page<CompanyResponseDto> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING COMPANY BY: {}", filter);
        var company = this.companyRepository.findAllByActiveTrue(pageable);
        return company.map(Company::response);
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public CompanyResponseDto save(CompanyRequestDto companyRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING COMPANY: {}", companyRequestDto.toString());
        var newCompany = this.companyRepository.save(companyRequestDto.convert());
        return newCompany.response();
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public CompanyResponseDto update(CompanyRequestDto companyRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Company company = this.findCompanyById(companyRequestDto.getId());
        company.setName(companyRequestDto.getName());
        company.setDocument(companyRequestDto.getDocument());
        log.debug(">>>UPDATING COMPANY: {}", companyRequestDto.toString());
        var updatedCompany = this.companyRepository.save(company);
        return updatedCompany.response();
    }

    @Override
    @CacheEvict(value = "Company", allEntries = true)
    public CompanyResponseDto deleteById(Long id) throws NoResultException {
        Company company = this.findCompanyById(id);
        company.setActive(false);
        log.debug(">>>DELETING COMPANY BY: {}", id);
        var deletedCompany = this.companyRepository.save(company);
        return deletedCompany.response();
    }

    private Company findCompanyById(Long id) {
        return this.companyRepository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("company")));
    }

}
