package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.company.dto.BranchRequestDto;
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
    public Branch findById(Long id) throws NoResultException {
        return this.branchRepository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("branch")));
    }

    @Override
    @Cacheable(value = "Branch", key = "#root.method.name")
    public List<Branch> findAll() {
        return this.branchRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Branch> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING BRANCH BY: {}", filter);
        return this.branchRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public Branch save(BranchRequestDto branchRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING BRANCH: {}", branchRequestDto.toString());
        return this.branchRepository.save(branchRequestDto.convert());
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public Branch update(BranchRequestDto branchRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Branch branch = this.findById(branchRequestDto.getId());
        branch.setName(branchRequestDto.getName());
        branch.setCompany(branchRequestDto.getCompany());
        log.debug(">>>UPDATING BRANCH: {}", branchRequestDto.toString());
        return this.branchRepository.save(branch);
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public Branch deleteById(Long id) throws NoResultException {
        Branch branch = findById(id);
        branch.setActive(false);
        log.debug(">>>DELETING BRANCH BY: {}", id);
        return this.branchRepository.save(branch);
    }
}
