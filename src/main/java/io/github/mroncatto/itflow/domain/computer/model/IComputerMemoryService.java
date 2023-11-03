package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerMemoryRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IComputerMemoryService extends IAbstractService<ComputerMemory, ComputerMemoryRequestDto> {
    ComputerMemory findById(Long id) throws NoResultException;
    Page<ComputerMemory> findAll(Pageable pageable, String filter);
    List<ComputerMemory> findAll(Specification<ComputerMemory> spec);
    ComputerMemory deleteById(Long id) throws NoResultException;
}
