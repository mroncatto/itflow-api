package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.device.model.ComputerCpu;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IComputerCpuController extends IAbstractController<ComputerCpu> {

    ResponseEntity<ComputerCpu> findById(Long id) throws NoResultException;
    ResponseEntity<Page<ComputerCpu>> findAll(int page, String filter);
    ResponseEntity<ComputerCpu> deleteById(Long id) throws NoResultException;
}
