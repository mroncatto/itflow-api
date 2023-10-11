package io.github.mroncatto.itflow.domain.software.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareDto;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareLicenseDto;
import io.github.mroncatto.itflow.domain.software.entity.Software;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface ISoftwareService extends IAbstractService<Software, SoftwareDto> {
    Software findById(Long id) throws NoResultException;
    Page<Software> findAll(Pageable pageable, String filter);
    Software deleteById(Long id) throws NoResultException;

    Software addLicense(Long id, SoftwareLicenseDto license, BindingResult result) throws NoResultException, BadRequestException;
}
