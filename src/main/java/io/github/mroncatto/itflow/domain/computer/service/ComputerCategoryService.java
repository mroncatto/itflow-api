package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerCategoryRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
import io.github.mroncatto.itflow.domain.computer.model.IComputerCategoryService;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerCategoryRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ComputerCategoryService extends AbstractService implements IComputerCategoryService {
    private final IComputerCategoryRepository repository;
    private final MessageService messageService;


    @Override
    public List<ComputerCategory> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public ComputerCategory save(ComputerCategoryRequestDto computerCategoryRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING COMPUTER CATEGORY: {}", computerCategoryRequestDto.toString());
        return this.repository.save(computerCategoryRequestDto.convert());
    }

    @Override
    public ComputerCategory update(ComputerCategoryRequestDto computerCategoryRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        var category = this.findById(computerCategoryRequestDto.getId());
        category.setName(computerCategoryRequestDto.getName());
        log.debug(">>>UPDATING COMPUTER CATEGORY: {}", computerCategoryRequestDto.toString());
        return this.repository.save(category);
    }

    @Override
    public ComputerCategory findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("computer_category")));
    }

    @Override
    public Page<ComputerCategory> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING COMPUTER CATEGORY BY: {}", filter);
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public ComputerCategory deleteById(Long id) throws NoResultException {
        ComputerCategory category = this.findById(id);
        category.setActive(false);
        log.debug(">>>DELETING COMPUTER CATEGORY BY: {}", id);
        return this.repository.save(category);
    }
}
