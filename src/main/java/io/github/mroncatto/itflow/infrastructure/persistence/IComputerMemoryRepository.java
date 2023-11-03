package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractComputerRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IComputerMemoryRepository extends IAbstractComputerRepository<ComputerMemory, Long>, JpaSpecificationExecutor<ComputerMemory> {
}
