package io.github.mroncatto.itflow.domain.software.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicenseKey;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface ISoftwareLicenseController extends IAbstractController<SoftwareLicense> {
    ResponseEntity<SoftwareLicense> findById(Long id) throws NoResultException;
    ResponseEntity<Page<SoftwareLicense>> findAll(int page, String filter);
    ResponseEntity<SoftwareLicense> deleteById(Long id) throws NoResultException;
    ResponseEntity<SoftwareLicense> addLicenseKey(Long id, SoftwareLicenseKey key, BindingResult result) throws NoResultException, BadRequestException;
    ResponseEntity<SoftwareLicense> RemoveLicenseKey(Long id, SoftwareLicenseKey key, BindingResult result) throws NoResultException, BadRequestException;
}
