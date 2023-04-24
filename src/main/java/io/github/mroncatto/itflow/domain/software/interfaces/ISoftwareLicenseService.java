package io.github.mroncatto.itflow.domain.software.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.software.model.SoftwareLicense;
import io.github.mroncatto.itflow.domain.software.model.SoftwareLicenseKey;
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
