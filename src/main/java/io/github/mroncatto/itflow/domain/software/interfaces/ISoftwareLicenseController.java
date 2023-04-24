package io.github.mroncatto.itflow.domain.software.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.software.model.SoftwareLicense;
import io.github.mroncatto.itflow.domain.software.model.SoftwareLicenseKey;
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
