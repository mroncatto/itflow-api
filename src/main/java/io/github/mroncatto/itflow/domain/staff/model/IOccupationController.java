package io.github.mroncatto.itflow.domain.staff.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;

public interface IOccupationController extends IAbstractController<Occupation> {
    ResponseEntity<Occupation> findById(Long id) throws NoResultException;
    ResponseEntity<Page<Occupation>> findAll(int page, String filter);
    ResponseEntity<Occupation> deleteById(Long id) throws NoResultException;

}
