package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.computer.model.IComputerStorageService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerStorageService extends AbstractService implements IComputerStorageService {
    private final IComputerStorageRepository repository;

    @Override
    public List<ComputerStorage> findAll() {
        return this.repository.findAll();
    }

    @Override
    public ComputerStorage save(ComputerStorage entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.repository.save(entity);
    }

    @Override
    public ComputerStorage update(ComputerStorage entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerStorage computerStorage = this.findById(entity.getId());
        computerStorage.setType(entity.getType());
        computerStorage.setBrandName(entity.getBrandName());
        return this.repository.save(computerStorage);
    }

    @Override
    public ComputerStorage findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("COMPUTER STORAGE NOT FOUND"));
    }

    @Override
    public Page<ComputerStorage> findAll(Pageable pageable, String filter) {
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public ComputerStorage deleteById(Long id) throws NoResultException {
        ComputerStorage computerStorage = this.findById(id);
        computerStorage.setActive(false);
        return this.repository.save(computerStorage);
    }
}
