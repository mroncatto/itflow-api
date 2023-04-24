package io.github.mroncatto.itflow.domain.company.model;

import io.github.mroncatto.itflow.domain.company.entity.Department;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDepartmentFilterController {
    ResponseEntity<List<Department>> findAllUsingByStaff();
}
