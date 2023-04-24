package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.company.entity.Department;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractCompanyRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDepartmentRepository extends IAbstractCompanyRepository<Department, Long> {

    @Query(value = "SELECT DISTINCT d FROM Department d JOIN Staff s ON d.id = s.department.id WHERE s.active = true")
    List<Department> findByStaffIsNotNull();

}
