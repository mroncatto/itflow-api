package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.company.model.IBranchService;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import io.github.mroncatto.itflow.infrastructure.persistence.IBranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService extends AbstractService implements IBranchService {
    private final IBranchRepository branchRepository;

    @Override
    public Branch findById(Long id) throws NoResultException {
        return this.branchRepository.findById(id).orElseThrow(() -> new NoResultException("BRANCH NOT FOUND"));
    }

    @Override
    @Cacheable(value = "Branch", key = "#root.method.name")
    public List<Branch> findAll() {
        return this.branchRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Branch> findAll(Pageable pageable, String filter) {
        return this.branchRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public Branch save(Branch entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.branchRepository.save(entity);
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public Branch update(Branch entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Branch branch = this.findById(entity.getId());
        branch.setName(entity.getName());
        branch.setCompany(entity.getCompany());
        return this.branchRepository.save(branch);
    }

    @Override
    @CacheEvict(value = "Branch", allEntries = true)
    public Branch deleteById(Long id) throws NoResultException {
        Branch branch = findById(id);
        branch.setActive(false);
        return this.branchRepository.save(branch);
    }
}
