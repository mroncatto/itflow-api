package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.company.dto.BranchRequestDto;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBranchService extends IAbstractService<Branch, BranchRequestDto> {
    Branch findById(Long id) throws NoResultException;
    Page<Branch> findAll(Pageable pageable, String filter);
    Branch deleteById(Long id) throws NoResultException;
}
