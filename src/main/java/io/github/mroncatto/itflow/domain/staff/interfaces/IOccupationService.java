package io.github.mroncatto.itflow.domain.staff.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.staff.model.Occupation;

import javax.persistence.NoResultException;

public interface IOccupationService extends IAbstractService<Occupation> {
    Occupation findById(Long id) throws NoResultException;
    Occupation deleteById(Long id) throws NoResultException;
}
