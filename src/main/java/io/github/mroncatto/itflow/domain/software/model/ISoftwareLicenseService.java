package io.github.mroncatto.itflow.domain.software.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.software.dto.LicenseKeyDto;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareLicenseDto;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface ISoftwareLicenseService extends IAbstractService<SoftwareLicense, SoftwareLicenseDto> {
    SoftwareLicense findById(Long id) throws NoResultException;
    Page<SoftwareLicense> findAll(Pageable pageable, String filter);
    SoftwareLicense deleteById(Long id) throws NoResultException;
    SoftwareLicense addLicenseKey(Long id, LicenseKeyDto key, BindingResult result) throws NoResultException, BadRequestException;
    SoftwareLicense RemoveLicenseKey(Long id, LicenseKeyDto key, BindingResult result) throws NoResultException, BadRequestException;
}
