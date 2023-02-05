package io.github.mroncatto.itflow.domain.staff.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;
import java.util.List;

public interface IStaffService extends IAbstractService<Staff> {
    Staff findById(String uuid) throws NoResultException;
    Page<Staff> findAll(Pageable pageable, String filter, List<String> departments, List<String> occupations);
    Staff deleteById(String uuid) throws NoResultException;
}
