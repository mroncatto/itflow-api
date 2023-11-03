package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractComputerRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IComputerStorageRepository extends IAbstractComputerRepository<ComputerStorage, Long>, JpaSpecificationExecutor<ComputerStorage> {
}
