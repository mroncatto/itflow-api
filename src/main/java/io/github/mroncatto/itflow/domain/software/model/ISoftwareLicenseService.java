package io.github.mroncatto.itflow.domain.software.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicenseKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface ISoftwareLicenseService extends IAbstractService<SoftwareLicense> {
    SoftwareLicense findById(Long id) throws NoResultException;
    Page<SoftwareLicense> findAll(Pageable pageable, String filter);
    SoftwareLicense deleteById(Long id) throws NoResultException;
    SoftwareLicense addLicenseKey(Long id, SoftwareLicenseKey key, BindingResult result) throws NoResultException, BadRequestException;
    SoftwareLicense RemoveLicenseKey(Long id, SoftwareLicenseKey key, BindingResult result) throws NoResultException, BadRequestException;
}
