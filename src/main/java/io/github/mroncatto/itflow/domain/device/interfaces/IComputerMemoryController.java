package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.device.model.ComputerMemory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IComputerMemoryController extends IAbstractController<ComputerMemory> {

    ResponseEntity<ComputerMemory> findById(Long id) throws NoResultException;
    ResponseEntity<Page<ComputerMemory>> findAll(int page, String filter);
    ResponseEntity<ComputerMemory> deleteById(Long id) throws NoResultException;
}
