package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.company.dto.DepartmentRequestDto;
import io.github.mroncatto.itflow.domain.company.dto.DepartmentResponseDto;
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
    public DepartmentResponseDto findById(Long id) throws NoResultException {
        return this.findDepartmentById(id).response();
    }


    @Override
    @Cacheable(value = "Department", key = "#root.method.name")
    public List<DepartmentResponseDto> findAll() {
        var departments = this.departmentRepository.findAllByActiveTrue(Sort.by(Sort.Direction.ASC, "branch.name", "name"));
        return departments.stream().map(Department::response).toList();
    }

    @Override
    public List<DepartmentResponseDto> findByStaffIsNotNull() {
        var departments = this.departmentRepository.findByStaffIsNotNull();
        return departments.stream().map(Department::response).toList();
    }

    @Override
    public Page<DepartmentResponseDto> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING DEPARTMENT BY: {}", filter);
        var pageDepartments = this.departmentRepository.findAllByActiveTrue(pageable);
        return pageDepartments.map(Department::response);
    }

    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public DepartmentResponseDto save(DepartmentRequestDto departmentRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING DEPARTMENT: {}", departmentRequestDto.toString());
        var newDepartment = this.departmentRepository.save(departmentRequestDto.convert());
        return newDepartment.response();
    }

    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public DepartmentResponseDto update(DepartmentRequestDto departmentRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Department dpto = this.findDepartmentById(departmentRequestDto.getId());
        dpto.setName(departmentRequestDto.getName());
        dpto.setBranch(departmentRequestDto.getBranch());
        log.debug(">>>UPDATING DEPARTMENT: {}", departmentRequestDto.toString());
        var updatedDepartment = this.departmentRepository.save(dpto);
        return updatedDepartment.response();
    }


    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public DepartmentResponseDto deleteById(Long id) throws NoResultException {
        Department  department = findDepartmentById(id);
        department.setActive(false);
        log.debug(">>>DELETING DEPARTMENT BY ID: {}", id);
        var deletedDepartment = this.departmentRepository.save(department);
        return deletedDepartment.response();
    }

    private Department findDepartmentById(Long id) {
        return this.departmentRepository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("department")));
    }
}
