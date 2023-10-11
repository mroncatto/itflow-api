package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerStorageDto;
import io.github.mroncatto.itflow.domain.computer.model.IComputerStorageService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
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
    public ComputerStorage save(ComputerStorageDto computerStorageDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var computerStorage = new ComputerStorage();
        BeanUtils.copyProperties(computerStorageDto, computerStorage);
        return this.repository.save(computerStorage);
    }

    @Override
    public ComputerStorage update(ComputerStorageDto computerStorageDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerStorage computerStorage = this.findById(computerStorageDto.getId());
        computerStorage.setType(computerStorageDto.getType());
        computerStorage.setBrandName(computerStorageDto.getBrandName());
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
