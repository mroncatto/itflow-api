package io.github.mroncatto.itflow.domain.staff.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;
import java.util.List;

public interface IStaffController extends IAbstractController<Staff> {
    ResponseEntity<Staff> findById(String id) throws NoResultException;
    ResponseEntity<Page<Staff>> findAll(int page, String filter, List<String> departments);
    ResponseEntity<Staff> deleteById(String id) throws NoResultException;
}
