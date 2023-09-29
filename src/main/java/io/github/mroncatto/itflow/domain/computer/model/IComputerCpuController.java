package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.NoResultException;

public interface IComputerCpuController extends IAbstractController<ComputerCpu> {

    ResponseEntity<ComputerCpu> findById(Long id) throws NoResultException;
    ResponseEntity<Page<ComputerCpu>> findAll(int page, String filter);
    ResponseEntity<ComputerCpu> deleteById(Long id) throws NoResultException;
}
