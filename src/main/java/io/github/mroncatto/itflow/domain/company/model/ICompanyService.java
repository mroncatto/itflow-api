package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.dto.CompanyRequestDto;
import io.github.mroncatto.itflow.domain.company.dto.CompanyResponseDto;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompanyService extends IAbstractService<CompanyResponseDto, CompanyRequestDto> {
    CompanyResponseDto findById(Long id) throws NoResultException;
    Page<CompanyResponseDto> findAll(Pageable pageable, String filter);
    CompanyResponseDto deleteById(Long id) throws NoResultException;
}
