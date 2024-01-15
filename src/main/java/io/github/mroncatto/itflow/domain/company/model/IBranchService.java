package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.dto.BranchRequestDto;
import io.github.mroncatto.itflow.domain.company.dto.BranchResponseDto;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBranchService extends IAbstractService<BranchResponseDto, BranchRequestDto> {
    BranchResponseDto findById(Long id) throws NoResultException;
    Page<BranchResponseDto> findAll(Pageable pageable, String filter);
    BranchResponseDto deleteById(Long id) throws NoResultException;
}
