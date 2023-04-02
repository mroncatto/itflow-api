package io.github.mroncatto.itflow.domain.company.interfaces;

import io.github.mroncatto.itflow.domain.company.model.Department;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDepartmentFilterController {
    ResponseEntity<List<Department>> findAllUsingByStaff();
}
