package io.github.mroncatto.itflow.domain.computer.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.model.ComputerCpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IComputerCpuService extends IAbstractService<ComputerCpu> {
    ComputerCpu findById(Long id) throws NoResultException;
    Page<ComputerCpu> findAll(Pageable pageable, String filter);
    ComputerCpu deleteById(Long id) throws NoResultException;
}
