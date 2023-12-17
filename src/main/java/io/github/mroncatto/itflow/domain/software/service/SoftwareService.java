package io.github.mroncatto.itflow.domain.software.service;

import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareRequestDto;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareLicenseRequestDto;
import io.github.mroncatto.itflow.domain.software.model.ISoftwareService;
import io.github.mroncatto.itflow.domain.software.entity.Software;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import io.github.mroncatto.itflow.infrastructure.persistence.ISoftwareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class SoftwareService extends AbstractService implements ISoftwareService {
    public static final String SOFTWARE = "software";
    private final ISoftwareRepository repository;
    private final MessageService messageService;

    @Override
    @Transactional(readOnly = true)
    public List<Software> findAll() {
        List<Software> allActiveSoftware = this.repository.findAllByActiveTrue();
        allActiveSoftware.forEach(software -> Hibernate.initialize(software.getLicenses()));
        return allActiveSoftware;
    }

    @Override
    public Software save(SoftwareRequestDto softwareRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var software = new Software();
        BeanUtils.copyProperties(softwareRequestDto, software);
        log.debug(">>>CREATING SOFTWARE: {}", softwareRequestDto);
        return this.repository.save(software);
    }

    @Override
    @Transactional
    public Software update(SoftwareRequestDto softwareRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Software software = this.repository.findById(softwareRequestDto.getId()).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound(SOFTWARE)));
        software.setName(softwareRequestDto.getName());
        software.setDeveloper(softwareRequestDto.getDeveloper());
        log.debug(">>>UPDATING SOFTWARE: {}", softwareRequestDto);
        return this.repository.save(software);
    }

    @Override
    @Transactional(readOnly = true)
    public Software findById(Long id) throws NoResultException {
        Software software = this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound(SOFTWARE)));
        Hibernate.initialize(software.getLicenses());
        return software;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Software> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING SOFTWARE BY: {}", filter);
        Page<Software> allActiveSoftwarePage = this.repository.findAllByActiveTrue(pageable);
        allActiveSoftwarePage.getContent().forEach(software -> Hibernate.initialize(software.getLicenses()));
        return allActiveSoftwarePage;
    }

    @Override
    @Transactional
    public Software deleteById(Long id) throws NoResultException {
        Software software = this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound(SOFTWARE)));
        software.setActive(false);
        Hibernate.initialize(software.getLicenses());
        log.debug(">>>DELETING SOFTWARE BY: {}", id);
        return this.repository.save(software);
    }

    @Override
    @Transactional
    public Software addLicense(Long id, SoftwareLicenseRequestDto licenseDto, BindingResult result) throws NoResultException, BadRequestException {
        validateResult(result);
        Software software = this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound(SOFTWARE)));
        var license = new SoftwareLicense();
        BeanUtils.copyProperties(licenseDto, license);
        license.setSoftware(software);
        software.getLicenses().add(license);
        log.debug(">>>ADD SOFTWARE LICENSE: {}", licenseDto);
        return this.repository.save(software);
    }
}
