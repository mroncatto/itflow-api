package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.company.dto.BranchRequestDto;
import io.github.mroncatto.itflow.domain.company.dto.BranchResponseDto;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import io.github.mroncatto.itflow.domain.company.model.IBranchService;
import io.github.mroncatto.itflow.infrastructure.persistence.IBranchRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class BranchService extends AbstractService implements IBranchService {
    private final IBranchRepository branchRepository;
    private final MessageService messageService;

    @Override
    public BranchResponseDto findById(Long id) throws NoResultException {
        return this.findBranchById(id).response();
    }

    @Override
    @Cacheable(value = "Branch", key = "#root.method.name")
    public List<BranchResponseDto> findAll() {
        var branches = this.branchRepository.findAllByActiveTrue();
        return branches.stream().map(Branch::response).toList();
    }

    @Override
    public Page<BranchResponseDto> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING BRANCH BY: {}", filter);
        var branches = this.branchRepository.findAllByActiveTrue(pageable);
        return branches.map(Branch::response);
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public BranchResponseDto save(BranchRequestDto branchRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING BRANCH: {}", branchRequestDto.toString());
        var newBranch = branchRequestDto.convert();
        this.branchRepository.save(newBranch);
        return newBranch.response();
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public BranchResponseDto update(BranchRequestDto branchRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Branch branch = this.findBranchById(branchRequestDto.getId());
        branch.setName(branchRequestDto.getName());
        branch.setCompany(branchRequestDto.getCompany());
        log.debug(">>>UPDATING BRANCH: {}", branchRequestDto.toString());
        var branchUpdated = this.branchRepository.save(branch);
        return branchUpdated.response();
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public BranchResponseDto deleteById(Long id) throws NoResultException {
        Branch branch = findBranchById(id);
        branch.setActive(false);
        log.debug(">>>DELETING BRANCH BY: {}", id);
        var branchDeleted = this.branchRepository.save(branch);
        return branchDeleted.response();
    }

    private Branch findBranchById(Long id) {
        return this.branchRepository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("branch")));
    }
}
