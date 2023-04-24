package io.github.mroncatto.itflow.domain.software.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.software.model.ISoftwareLicenseService;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicenseKey;
import io.github.mroncatto.itflow.infrastructure.persistence.ISoftwareLicenseRepostory;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoftwareLicenseService extends AbstractService implements ISoftwareLicenseService {
    private final ISoftwareLicenseRepostory repository;

    @Override
    @Transactional(readOnly = true)
    public List<SoftwareLicense> findAll() {
        List<SoftwareLicense> licenses = this.repository.findAllByActiveTrue();
        licenses.forEach(lic -> Hibernate.initialize(lic.getKeys()));
        return licenses;
    }

    @Override
    public SoftwareLicense save(SoftwareLicense entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.repository.save(entity);
    }

    @Override
    @Transactional
    public SoftwareLicense update(SoftwareLicense entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        SoftwareLicense license = this.findById(entity.getId());
        license.setDescription(entity.getDescription());
        license.setCode(entity.getCode());
        return this.repository.save(license);
    }

    @Override
    @Transactional(readOnly = true)
    public SoftwareLicense findById(Long id) throws NoResultException {
        SoftwareLicense license = this.repository.findById(id).orElseThrow(() -> new NoResultException("SOFTWARE LICENSE NOT FOUND"));
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
    public SoftwareLicense addLicenseKey(Long id, SoftwareLicenseKey key, BindingResult result) throws NoResultException, BadRequestException {
        validateResult(result);
        SoftwareLicense license = this.findById(id);
        Hibernate.initialize(license.getKeys());
        key.setSoftwareLicense(license);
        license.getKeys().add(key);
        return this.repository.save(license);
    }

    @Override
    @Transactional
    public SoftwareLicense RemoveLicenseKey(Long id, SoftwareLicenseKey key, BindingResult result) throws NoResultException, BadRequestException {
        validateResult(result);
        SoftwareLicense license = this.findById(id);
        Hibernate.initialize(license.getKeys());
        license.getKeys().removeIf(k -> k.getId().equals(key.getId()));
        return this.repository.save(license);
    }
}
