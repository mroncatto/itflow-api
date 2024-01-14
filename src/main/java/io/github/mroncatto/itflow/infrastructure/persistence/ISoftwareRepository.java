package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.software.entity.Software;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractSoftwareRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ISoftwareRepository extends IAbstractSoftwareRepository<Software, Long>, JpaSpecificationExecutor<Software> {
}
