package io.github.mroncatto.itflow.domain.company.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.company.interfaces.IDepartmentService;
import io.github.mroncatto.itflow.domain.company.model.Department;
import io.github.mroncatto.itflow.domain.company.repository.IDepartmentRepository;
import lombok.RequiredArgsConstructor;
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
    public List<Department> findAll() {
        return this.departmentRepository.findAllByActiveTrue(Sort.by(Sort.Direction.ASC, "branch.name", "name"));
    }

    @Override
    public Page<Department> findAll(Pageable pageable, String filter) {
        return this.departmentRepository.findAllByActiveTrue(pageable);
    }

    @Override
    public Department save(Department entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.departmentRepository.save(entity);
    }

    @Override
    public Department update(Department entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Department dpto = this.findById(entity.getId());
        dpto.setName(entity.getName());
        dpto.setBranch(entity.getBranch());
        return this.departmentRepository.save(dpto);
    }


    @Override
    public Department deleteById(Long id) throws NoResultException {
        Department  department = findById(id);
        department.setActive(false);
        return this.departmentRepository.save(department);
    }
}
