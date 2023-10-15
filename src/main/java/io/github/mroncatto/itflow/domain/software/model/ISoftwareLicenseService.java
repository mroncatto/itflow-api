package io.github.mroncatto.itflow.domain.software.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.software.dto.LicenseKeyRequestDto;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareLicenseRequestDto;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface ISoftwareLicenseService extends IAbstractService<SoftwareLicense, SoftwareLicenseRequestDto> {
    SoftwareLicense findById(Long id) throws NoResultException;
    Page<SoftwareLicense> findAll(Pageable pageable, String filter);
    SoftwareLicense deleteById(Long id) throws NoResultException;
    SoftwareLicense addLicenseKey(Long id, LicenseKeyRequestDto key, BindingResult result) throws NoResultException, BadRequestException;
    SoftwareLicense RemoveLicenseKey(Long id, LicenseKeyRequestDto key, BindingResult result) throws NoResultException, BadRequestException;
}
