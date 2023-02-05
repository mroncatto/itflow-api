package io.github.mroncatto.itflow.domain.staff.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.staff.model.Occupation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IOccupationService extends IAbstractService<Occupation> {
    Occupation findById(Long id) throws NoResultException;
    Page<Occupation> findAll(Pageable pageable, String filter);
    Occupation deleteById(Long id) throws NoResultException;
}
