package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.company.dto.DepartmentRequestDto;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import io.github.mroncatto.itflow.domain.company.model.IDepartmentService;
import io.github.mroncatto.itflow.infrastructure.persistence.IDepartmentRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DepartmentService extends AbstractService implements IDepartmentService {
    private final IDepartmentRepository departmentRepository;
    private final MessageService messageService;

    @Override
    public Department findById(Long id) throws NoResultException {
        return this.departmentRepository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("department")));
    }


    @Override
    @Cacheable(value = "Department", key = "#root.method.name")
    public List<Department> findAll() {
        return this.departmentRepository.findAllByActiveTrue(Sort.by(Sort.Direction.ASC, "branch.name", "name"));
    }

    public List<Department> findByStaffIsNotNull() {
        return this.departmentRepository.findByStaffIsNotNull();
    }

    @Override
    public Page<Department> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING DEPARTMENT BY: {}", filter);
        return this.departmentRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public Department save(DepartmentRequestDto departmentRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING DEPARTMENT: {}", departmentRequestDto.toString());
        return this.departmentRepository.save(departmentRequestDto.convert());
    }

    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public Department update(DepartmentRequestDto departmentRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Department dpto = this.findById(departmentRequestDto.getId());
        dpto.setName(departmentRequestDto.getName());
        dpto.setBranch(departmentRequestDto.getBranch());
        log.debug(">>>UPDATING DEPARTMENT: {}", departmentRequestDto.toString());
        return this.departmentRepository.save(dpto);
    }


    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public Department deleteById(Long id) throws NoResultException {
        Department  department = findById(id);
        department.setActive(false);
        log.debug(">>>DELETING DEPARTMENT BY ID: {}", id);
        return this.departmentRepository.save(department);
    }
}
