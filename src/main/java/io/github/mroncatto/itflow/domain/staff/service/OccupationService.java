package io.github.mroncatto.itflow.domain.staff.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.staff.model.IOccupationService;
import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import io.github.mroncatto.itflow.infrastructure.persistence.IOccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OccupationService extends AbstractService implements IOccupationService {
    private final IOccupationRepository occupationRepository;

    @Override
    @Cacheable(value = "Occupation", key = "#root.method.name")
    public List<Occupation> findAll() {
        return this.occupationRepository.findAllByActiveTrue();
    }

    public List<Occupation> findByStaffIsNotNull() {
        return this.occupationRepository.findByStaffIsNotNull();
    }

    @Override
    public Page<Occupation> findAll(Pageable pageable, String filter) {
        return this.occupationRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Occupation", allEntries = true)
    public Occupation save(Occupation entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.occupationRepository.save(entity);
    }

    @Override
    @CacheEvict(value = "Occupation", allEntries = true)
    public Occupation update(Occupation entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Occupation occupation = this.findById(entity.getId());
        occupation.setName(entity.getName());
        return this.occupationRepository.save(occupation);
    }

    @Override
    public Occupation findById(Long id) throws NoResultException {
        return this.occupationRepository.findById(id).orElseThrow(() -> new NoResultException("OCCUPATION NOT FOUND"));
    }

    @Override
    @CacheEvict(value = "Occupation", allEntries = true)
    public Occupation deleteById(Long id) throws NoResultException {
        Occupation occupation = this.findById(id);
        occupation.setActive(false);
        return this.occupationRepository.save(occupation);
    }
}
