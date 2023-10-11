package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.company.dto.DepartmentDto;
import io.github.mroncatto.itflow.domain.company.model.IDepartmentService;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import io.github.mroncatto.itflow.infrastructure.persistence.IDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService extends AbstractService implements IDepartmentService {
    private final IDepartmentRepository departmentRepository;

    @Override
    public Department findById(Long id) throws NoResultException {
        return this.departmentRepository.findById(id).orElseThrow(() -> new NoResultException("DEPARTMENT NOT FOUND"));
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
        return this.departmentRepository.findAllByActiveTrue(pageable);
    }

    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public Department save(DepartmentDto dto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var department = new Department();
        BeanUtils.copyProperties(dto, department);
        return this.departmentRepository.save(department);
    }

    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public Department update(DepartmentDto dto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Department dpto = this.findById(dto.getId());
        dpto.setName(dto.getName());
        dpto.setBranch(dto.getBranch());
        return this.departmentRepository.save(dpto);
    }


    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public Department deleteById(Long id) throws NoResultException {
        Department  department = findById(id);
        department.setActive(false);
        return this.departmentRepository.save(department);
    }
}
