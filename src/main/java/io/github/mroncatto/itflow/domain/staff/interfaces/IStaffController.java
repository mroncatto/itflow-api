package io.github.mroncatto.itflow.domain.staff.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IStaffController extends IAbstractController<Staff> {
    ResponseEntity<Staff> findById(String id) throws NoResultException;
    ResponseEntity<Staff> deleteById(String id) throws NoResultException;
}
