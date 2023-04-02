package io.github.mroncatto.itflow.domain.computer.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.model.ComputerMemory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IComputerMemoryService extends IAbstractService<ComputerMemory> {
    ComputerMemory findById(Long id) throws NoResultException;
    Page<ComputerMemory> findAll(Pageable pageable, String filter);
    ComputerMemory deleteById(Long id) throws NoResultException;
}
