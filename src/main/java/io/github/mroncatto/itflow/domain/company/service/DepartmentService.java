package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.company.interfaces.IDepartmentService;
import io.github.mroncatto.itflow.domain.company.model.Department;
import io.github.mroncatto.itflow.domain.company.repository.IDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
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
    public Department save(Department entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.departmentRepository.save(entity);
    }

    @Override
    @CacheEvict(value = "Department", allEntries = true)
    public Department update(Department entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Department dpto = this.findById(entity.getId());
        dpto.setName(entity.getName());
        dpto.setBranch(entity.getBranch());
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
