package io.github.mroncatto.itflow.domain.staff.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;
import java.util.List;

public interface IStaffController extends IAbstractController<Staff> {
    ResponseEntity<Staff> findById(String id) throws NoResultException;
    ResponseEntity<Page<Staff>> findAll(int page, String filter, List<String> departments, List<String> occupations);
    ResponseEntity<Staff> deleteById(String id) throws NoResultException;
}
