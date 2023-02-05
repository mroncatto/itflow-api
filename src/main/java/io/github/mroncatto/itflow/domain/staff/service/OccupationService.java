package io.github.mroncatto.itflow.domain.staff.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.staff.interfaces.IOccupationService;
import io.github.mroncatto.itflow.domain.staff.model.Occupation;
import io.github.mroncatto.itflow.domain.staff.repository.IOccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OccupationService extends AbstractService implements IOccupationService {
    private final IOccupationRepository occupationRepository;

    @Override
    public List<Occupation> findAll() {
        return this.occupationRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Occupation> findAll(Pageable pageable, String filter) {
        return this.occupationRepository.findAllByActiveTrue(pageable);
    }

    @Override
    public Occupation save(Occupation entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.occupationRepository.save(entity);
    }

    @Override
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
    public Occupation deleteById(Long id) throws NoResultException {
        Occupation occupation = this.findById(id);
        occupation.setActive(false);
        return this.occupationRepository.save(occupation);
    }
}
