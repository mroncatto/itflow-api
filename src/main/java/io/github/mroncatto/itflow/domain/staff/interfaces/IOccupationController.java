package io.github.mroncatto.itflow.domain.staff.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.staff.model.Occupation;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IOccupationController extends IAbstractController<Occupation> {
    ResponseEntity<Occupation> findById(Long id) throws NoResultException;
    ResponseEntity<Occupation> deleteById(Long id) throws NoResultException;

}
