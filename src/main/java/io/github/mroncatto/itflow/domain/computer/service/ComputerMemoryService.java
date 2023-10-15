package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerMemoryRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import io.github.mroncatto.itflow.domain.computer.model.IComputerMemoryService;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerMemoryRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ComputerMemoryService extends AbstractService implements IComputerMemoryService {
    private final IComputerMemoryRepository repository;
    private final MessageService messageService;


    @Override
    public List<ComputerMemory> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public ComputerMemory save(ComputerMemoryRequestDto computerMemoryRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING COMPUTER MEMORY: {}", computerMemoryRequestDto.toString());
        return this.repository.save(computerMemoryRequestDto.convert());
    }

    @Override
    public ComputerMemory update(ComputerMemoryRequestDto computerMemoryRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerMemory memory = this.findById(computerMemoryRequestDto.getId());
        memory.setBrandName(computerMemoryRequestDto.getBrandName());
        memory.setFrequency(computerMemoryRequestDto.getFrequency());
        memory.setType(computerMemoryRequestDto.getType());
        memory.setSize(computerMemoryRequestDto.getSize());
        log.debug(">>>UPDATING COMPUTER MEMORY: {}", computerMemoryRequestDto.toString());
        return this.repository.save(memory);
    }

    @Override
    public ComputerMemory findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("computer_memory")));
    }

    @Override
    public Page<ComputerMemory> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING COMPUTER MEMORY BY: {}", filter);
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public ComputerMemory deleteById(Long id) throws NoResultException {
        ComputerMemory memory = this.findById(id);
        memory.setActive(false);
        log.debug(">>>DELETING COMPUTER MEMORY BY: {}", id);
        return this.repository.save(memory);
    }
}
