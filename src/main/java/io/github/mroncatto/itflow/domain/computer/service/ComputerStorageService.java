package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerStorageRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import io.github.mroncatto.itflow.domain.computer.model.IComputerStorageService;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerStorageRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ComputerStorageService extends AbstractService implements IComputerStorageService {
    private final IComputerStorageRepository repository;
    private final MessageService messageService;

    @Override
    public List<ComputerStorage> findAll() {
        return this.repository.findAll();
    }

    @Override
    public ComputerStorage save(ComputerStorageRequestDto computerStorageRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING COMPUTER STORAGE: {}", computerStorageRequestDto.toString());
        return this.repository.save(computerStorageRequestDto.convert());
    }

    @Override
    public ComputerStorage update(ComputerStorageRequestDto computerStorageRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerStorage computerStorage = this.findById(computerStorageRequestDto.getId());
        computerStorage.setType(computerStorageRequestDto.getType());
        computerStorage.setBrandName(computerStorageRequestDto.getBrandName());
        log.debug(">>>UPDATING COMPUTER STORAGE: {}", computerStorageRequestDto.toString());
        return this.repository.save(computerStorage);
    }

    @Override
    public ComputerStorage findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("computer_storage")));
    }

    @Override
    public Page<ComputerStorage> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING COMPUTER STORAGE BY: {}", filter);
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public ComputerStorage deleteById(Long id) throws NoResultException {
        ComputerStorage computerStorage = this.findById(id);
        computerStorage.setActive(false);
        log.debug(">>>DELETING COMPUTER STORAGE BY: {}", id);
        return this.repository.save(computerStorage);
    }

    @Override
    public List<ComputerStorage> findAll(Specification<ComputerStorage> spec) {
        return this.repository.findAll(spec)
                .stream()
                .sorted(Comparator.comparing(ComputerStorage::getBrandName))
                .limit(10)
                .collect(Collectors.toList());
        // TODO: Filtrar e ordenar a nivel de banco
    }
}
