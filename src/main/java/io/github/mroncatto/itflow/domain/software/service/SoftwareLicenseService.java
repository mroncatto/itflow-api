package io.github.mroncatto.itflow.domain.software.service;

import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.software.dto.LicenseKeyRequestDto;
import io.github.mroncatto.itflow.domain.software.dto.SoftwareLicenseRequestDto;
import io.github.mroncatto.itflow.domain.software.model.ISoftwareLicenseService;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicenseKey;
import io.github.mroncatto.itflow.infrastructure.persistence.ISoftwareLicenseRepostory;
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
public class SoftwareLicenseService extends AbstractService implements ISoftwareLicenseService {
    private final ISoftwareLicenseRepostory repository;
    private final MessageService messageService;

    @Override
    @Transactional(readOnly = true)
    public List<SoftwareLicense> findAll() {
        List<SoftwareLicense> licenses = this.repository.findAllByActiveTrue();
        licenses.forEach(lic -> Hibernate.initialize(lic.getKeys()));
        return licenses;
    }

    @Override
    public SoftwareLicense save(SoftwareLicenseRequestDto softwareLicenseRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var softwareLicense = new SoftwareLicense();
        BeanUtils.copyProperties(softwareLicenseRequestDto, softwareLicense);
        log.debug(">>>CREATING SOFTWARE LICENSE: {}", softwareLicenseRequestDto);
        return this.repository.save(softwareLicense);
    }

    @Override
    @Transactional
    public SoftwareLicense update(SoftwareLicenseRequestDto softwareLicenseRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        SoftwareLicense license = this.findById(softwareLicenseRequestDto.getId());
        license.setDescription(softwareLicenseRequestDto.getDescription());
        license.setCode(softwareLicenseRequestDto.getCode());
        log.debug(">>>UPDATING SOFTWARE LICENSE: {}", softwareLicenseRequestDto);
        return this.repository.save(license);
    }

    @Override
    @Transactional(readOnly = true)
    public SoftwareLicense findById(Long id) throws NoResultException {
        SoftwareLicense license = this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("software_license")));
        Hibernate.initialize(license.getKeys());
        return license;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoftwareLicense> findAll(Pageable pageable, String filter) {
        Page<SoftwareLicense> licensesPage = this.repository.findAllByActiveTrue(pageable);
        licensesPage.getContent().forEach(lic -> Hibernate.initialize(lic.getKeys()));
        return licensesPage;
    }

    @Override
    @Transactional(readOnly = true)
    public SoftwareLicense deleteById(Long id) throws NoResultException {
        SoftwareLicense license = this.findById(id);
        license.setActive(false);
        Hibernate.initialize(license.getKeys());
        //TODO: Validar licensa com keys em uso!
        return this.repository.save(license);
    }

    @Override
    @Transactional
    public SoftwareLicense addLicenseKey(Long id, LicenseKeyRequestDto licenseKeyRequestDto, BindingResult result) throws NoResultException, BadRequestException {
        validateResult(result);
        SoftwareLicense license = this.findById(id);
        Hibernate.initialize(license.getKeys());
        licenseKeyRequestDto.setSoftwareLicense(license);
        var licenseKey = new SoftwareLicenseKey();
        BeanUtils.copyProperties(licenseKeyRequestDto, licenseKey);
        license.getKeys().add(licenseKey);
        log.debug(">>>ADD SOFTWARE LICENSE KEY: {}", licenseKeyRequestDto);
        return this.repository.save(license);
    }

    @Override
    @Transactional
    public SoftwareLicense RemoveLicenseKey(Long id, LicenseKeyRequestDto licenseKeyRequestDto, BindingResult result) throws NoResultException, BadRequestException {
        validateResult(result);
        SoftwareLicense license = this.findById(id);
        Hibernate.initialize(license.getKeys());
        license.getKeys().removeIf(k -> k.getId().equals(licenseKeyRequestDto.getId()));
        log.debug(">>>REMOVE SOFTWARE LICENSE KEY: {}", licenseKeyRequestDto);
        return this.repository.save(license);
    }
}
