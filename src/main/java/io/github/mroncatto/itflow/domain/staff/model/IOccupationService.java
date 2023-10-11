package io.github.mroncatto.itflow.domain.staff.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.staff.dto.OccupationDto;
import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

import java.util.List;

public interface IOccupationService extends IAbstractService<Occupation, OccupationDto> {
    Occupation findById(Long id) throws NoResultException;
    Page<Occupation> findAll(Pageable pageable, String filter);
    Occupation deleteById(Long id) throws NoResultException;
    List<Occupation> findByStaffIsNotNull();
}
