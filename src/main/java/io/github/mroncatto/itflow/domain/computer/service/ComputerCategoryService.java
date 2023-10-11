package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerCategoryDto;
import io.github.mroncatto.itflow.domain.computer.model.IComputerCategoryService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
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
    public ComputerCategory save(ComputerCategoryDto computerCategoryDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var computerCategory = new ComputerCategory();
        BeanUtils.copyProperties(computerCategoryDto, computerCategory);
        return this.repository.save(computerCategory);
    }

    @Override
    public ComputerCategory update(ComputerCategoryDto computerCategoryDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerCategory category = this.findById(computerCategoryDto.getId());
        category.setName(computerCategoryDto.getName());
        return this.repository.save(category);
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
