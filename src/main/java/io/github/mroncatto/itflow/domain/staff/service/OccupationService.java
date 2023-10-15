package io.github.mroncatto.itflow.domain.staff.service;

import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.staff.dto.OccupationRequestDto;
import io.github.mroncatto.itflow.domain.staff.model.IOccupationService;
import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import io.github.mroncatto.itflow.infrastructure.persistence.IOccupationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class OccupationService extends AbstractService implements IOccupationService {
    private final IOccupationRepository occupationRepository;
    private final MessageService messageService;

    @Override
    @Cacheable(value = "Occupation", key = "#root.method.name")
    public List<Occupation> findAll() {
        return this.occupationRepository.findAllByActiveTrue();
    }

    @Override
    public List<Occupation> findByStaffIsNotNull() {
        return this.occupationRepository.findByStaffIsNotNull();
    }

    @Override
    public Page<Occupation> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING OCCUPATION BY: {}", filter);
        return this.occupationRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Occupation", allEntries = true)
    public Occupation save(OccupationRequestDto occupationRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var occupation = new Occupation();
        BeanUtils.copyProperties(occupationRequestDto, occupation);
        log.debug(">>>CREATING OCCUPATION: {}", occupationRequestDto);
        return this.occupationRepository.save(occupation);
    }

    @Override
    @CacheEvict(value = "Occupation", allEntries = true)
    public Occupation update(OccupationRequestDto occupationRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        var occupation = this.findById(occupationRequestDto.getId());
        occupation.setName(occupationRequestDto.getName());
        log.debug(">>>UPDATING OCCUPATION: {}", occupationRequestDto);
        return this.occupationRepository.save(occupation);
    }

    @Override
    public Occupation findById(Long id) throws NoResultException {
        return this.occupationRepository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("occupation")));
    }

    @Override
    @CacheEvict(value = "Occupation", allEntries = true)
    public Occupation deleteById(Long id) throws NoResultException {
        Occupation occupation = this.findById(id);
        occupation.setActive(false);
        log.debug(">>>DELETING OCCUPATION BY: {}", id);
        return this.occupationRepository.save(occupation);
    }
}
