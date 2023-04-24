package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IComputerCategoryService extends IAbstractService<ComputerCategory> {
    ComputerCategory findById(Long id) throws NoResultException;
    Page<ComputerCategory> findAll(Pageable pageable, String filter);
    ComputerCategory deleteById(Long id) throws NoResultException;
}
