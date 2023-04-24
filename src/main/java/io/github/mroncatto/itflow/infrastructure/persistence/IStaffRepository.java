package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractStaffRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface IStaffRepository extends IAbstractStaffRepository<Staff, UUID>, JpaSpecificationExecutor<Staff> {

    List<Staff> findAllByEmail(String email);


}
