package io.github.mroncatto.itflow.domain.staff.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.staff.dto.StaffDto;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;
import java.util.List;

public interface IStaffService extends IAbstractService<Staff, StaffDto> {
    Staff findById(String uuid) throws NoResultException;
    Page<Staff> findAll(Pageable pageable, String filter, List<String> departments, List<String> occupations);
    Staff deleteById(String uuid) throws NoResultException;
}
