package io.github.mroncatto.itflow.domain.staff.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.staff.dto.StaffRequestDto;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IStaffService extends IAbstractService<Staff, StaffRequestDto> {
    Staff findById(String uuid) throws NoResultException;
    Page<Staff> findAll(Specification<Staff> spec, Pageable pageable);
    Staff deleteById(String uuid) throws NoResultException;
}
