package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.dto.BranchRequestDto;
import io.github.mroncatto.itflow.domain.company.dto.BranchResDto;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBranchService extends IAbstractService<BranchResDto, BranchRequestDto> {
    BranchResDto findById(Long id) throws NoResultException;
    Page<BranchResDto> findAll(Pageable pageable, String filter);
    BranchResDto deleteById(Long id) throws NoResultException;
}
