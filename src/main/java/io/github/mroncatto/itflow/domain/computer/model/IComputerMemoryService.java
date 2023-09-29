package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

public interface IComputerMemoryService extends IAbstractService<ComputerMemory> {
    ComputerMemory findById(Long id) throws NoResultException;
    Page<ComputerMemory> findAll(Pageable pageable, String filter);
    ComputerMemory deleteById(Long id) throws NoResultException;
}
