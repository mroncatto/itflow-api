package io.github.mroncatto.itflow.domain.software.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.software.entity.Software;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface ISoftwareService extends IAbstractService<Software> {
    Software findById(Long id) throws NoResultException;
    Page<Software> findAll(Pageable pageable, String filter);
    Software deleteById(Long id) throws NoResultException;

    Software addLicense(Long id, SoftwareLicense license, BindingResult result) throws NoResultException, BadRequestException;
}
