package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.computer.model.IComputerMemoryService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerMemoryService extends AbstractService implements IComputerMemoryService {

    private final IComputerMemoryRepository repository;


    @Override
    public List<ComputerMemory> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public ComputerMemory save(ComputerMemory entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.repository.save(entity);
    }

    @Override
    public ComputerMemory update(ComputerMemory entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerMemory memory = this.findById(entity.getId());
        memory.setBrandName(entity.getBrandName());
        memory.setFrequency(entity.getFrequency());
        memory.setType(entity.getType());
        memory.setSize(entity.getSize());
        return this.repository.save(memory);
    }

    @Override
    public ComputerMemory findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("COMPUTER MEMORY NOT FOUND"));
    }

    @Override
    public Page<ComputerMemory> findAll(Pageable pageable, String filter) {
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public ComputerMemory deleteById(Long id) throws NoResultException {
        ComputerMemory memory = this.findById(id);
        memory.setActive(false);
        return this.repository.save(memory);
    }
}
