package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.computer.interfaces.IComputerCategoryService;
import io.github.mroncatto.itflow.domain.computer.model.ComputerCategory;
import io.github.mroncatto.itflow.domain.computer.repository.IComputerCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerCategoryService extends AbstractService implements IComputerCategoryService {
    private final IComputerCategoryRepository repository;


    @Override
    public List<ComputerCategory> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public ComputerCategory save(ComputerCategory entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.repository.save(entity);
    }

    @Override
    public ComputerCategory update(ComputerCategory entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerCategory category = this.findById(entity.getId());
        category.setName(entity.getName());
        return this.repository.save(entity);
    }

    @Override
    public ComputerCategory findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("COMPUTER CATEGORY NOT FOUND"));
    }

    @Override
    public Page<ComputerCategory> findAll(Pageable pageable, String filter) {
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public ComputerCategory deleteById(Long id) throws NoResultException {
        ComputerCategory category = this.findById(id);
        category.setActive(false);
        return this.repository.save(category);
    }
}
