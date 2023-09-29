package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

public interface IComputerCpuService extends IAbstractService<ComputerCpu> {
    ComputerCpu findById(Long id) throws NoResultException;
    Page<ComputerCpu> findAll(Pageable pageable, String filter);
    ComputerCpu deleteById(Long id) throws NoResultException;
}
