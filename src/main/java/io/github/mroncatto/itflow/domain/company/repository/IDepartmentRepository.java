package io.github.mroncatto.itflow.domain.company.repository;

import io.github.mroncatto.itflow.domain.company.model.Department;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDepartmentRepository extends IAbstractCompanyRepository<Department, Long> {

    @Query(value = "SELECT DISTINCT d FROM Department d JOIN Staff s ON d.id = s.department.id WHERE s.active = true")
    List<Department> findByStaffIsNotNull();

}
