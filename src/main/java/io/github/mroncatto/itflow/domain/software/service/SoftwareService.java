package io.github.mroncatto.itflow.domain.software.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.software.interfaces.ISoftwareService;
import io.github.mroncatto.itflow.domain.software.model.Software;
import io.github.mroncatto.itflow.domain.software.model.SoftwareLicense;
import io.github.mroncatto.itflow.domain.software.repository.ISoftwareRepository;
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
public class SoftwareService extends AbstractService implements ISoftwareService {
    private final ISoftwareRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Software> findAll() {
        List<Software> allActiveSoftware = this.repository.findAllByActiveTrue();
        allActiveSoftware.forEach(software -> Hibernate.initialize(software.getLicenses()));
        return allActiveSoftware;
    }

    @Override
    public Software save(Software entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.repository.save(entity);
    }

    @Override
    public Software update(Software entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Software software = this.findById(entity.getId());
        software.setName(entity.getName());
        software.setDeveloper(entity.getDeveloper());
        return this.repository.save(software);
    }

    @Override
    @Transactional(readOnly = true)
    public Software findById(Long id) throws NoResultException {
        Software software = this.repository.findById(id).orElseThrow(() -> new NoResultException("SOFTWARE NOT FOUND"));
        Hibernate.initialize(software.getLicenses());
        return software;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Software> findAll(Pageable pageable, String filter) {
        Page<Software> allActiveSoftwarePage = this.repository.findAllByActiveTrue(pageable);
        allActiveSoftwarePage.getContent().forEach(software -> {
            Hibernate.initialize(software.getLicenses());
        });
        return allActiveSoftwarePage;
    }

    @Override
    @Transactional
    public Software deleteById(Long id) throws NoResultException {
        Software software = this.findById(id);
        software.setActive(false);
        Hibernate.initialize(software.getLicenses());
        return this.repository.save(software);
    }

    @Override
    @Transactional
    public Software addLicense(Long id, SoftwareLicense license, BindingResult result) throws NoResultException, BadRequestException {
        validateResult(result);
        Software software = this.findById(id);
        software.getLicenses().add(license);
        return this.repository.save(software);
    }
}
