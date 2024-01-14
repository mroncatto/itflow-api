package io.github.mroncatto.itflow.domain.software.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareLicenseRequestDto;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareRequestDto;
import io.github.mroncatto.itflow.domain.software.entity.Software;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ISoftwareService extends IAbstractService<Software, SoftwareRequestDto> {
    Software findById(Long id) throws NoResultException;

    List<Software> findAll(Specification<Software> spec);
    Page<Software> findAll(Pageable pageable, String filter);
    Software deleteById(Long id) throws NoResultException;

    Software addLicense(Long id, SoftwareLicenseRequestDto license, BindingResult result) throws NoResultException, BadRequestException;
}
