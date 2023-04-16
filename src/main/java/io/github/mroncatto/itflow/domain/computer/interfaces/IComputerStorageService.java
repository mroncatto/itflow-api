package io.github.mroncatto.itflow.domain.computer.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.model.ComputerStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IComputerStorageService  extends IAbstractService<ComputerStorage> {
    ComputerStorage findById(Long id) throws NoResultException;
    Page<ComputerStorage> findAll(Pageable pageable, String filter);
    ComputerStorage deleteById(Long id) throws NoResultException;
}
