package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerStorageRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

public interface IComputerStorageService  extends IAbstractService<ComputerStorage, ComputerStorageRequestDto> {
    ComputerStorage findById(Long id) throws NoResultException;
    Page<ComputerStorage> findAll(Pageable pageable, String filter);
    ComputerStorage deleteById(Long id) throws NoResultException;
}
