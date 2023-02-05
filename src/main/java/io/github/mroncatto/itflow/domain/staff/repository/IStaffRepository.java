package io.github.mroncatto.itflow.domain.staff.repository;

import io.github.mroncatto.itflow.domain.staff.model.Staff;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface IStaffRepository extends IAbstractStaffRepository<Staff, UUID>, JpaSpecificationExecutor<Staff> {
}
