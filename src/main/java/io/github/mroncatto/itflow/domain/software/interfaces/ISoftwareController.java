package io.github.mroncatto.itflow.domain.software.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.software.model.Software;
import io.github.mroncatto.itflow.domain.software.model.SoftwareLicense;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface ISoftwareController extends IAbstractController<Software> {
    ResponseEntity<Software> findById(Long id) throws NoResultException;
    ResponseEntity<Page<Software>> findAll(int page, String filter);
    ResponseEntity<Software> deleteById(Long id) throws NoResultException;
    ResponseEntity<Software> addLicense(Long id, SoftwareLicense license, BindingResult result) throws NoResultException, BadRequestException;
}
