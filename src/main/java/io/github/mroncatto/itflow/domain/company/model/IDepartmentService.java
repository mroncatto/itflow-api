package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.dto.DepartmentRequestDto;
import io.github.mroncatto.itflow.domain.company.dto.DepartmentResponseDto;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDepartmentService extends IAbstractService<DepartmentResponseDto, DepartmentRequestDto> {
    DepartmentResponseDto findById(Long id) throws NoResultException;

    List<DepartmentResponseDto> findByStaffIsNotNull();

    Page<DepartmentResponseDto> findAll(Pageable pageable, String filter);
    DepartmentResponseDto deleteById(Long id) throws NoResultException;
}
