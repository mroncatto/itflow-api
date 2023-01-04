package io.github.mroncatto.itflow.domain.staff.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.staff.model.Staff;

import javax.persistence.NoResultException;

public interface IStaffService extends IAbstractService<Staff> {
    Staff findById(String uuid) throws NoResultException;
    Staff deleteById(String uuid) throws NoResultException;
}
