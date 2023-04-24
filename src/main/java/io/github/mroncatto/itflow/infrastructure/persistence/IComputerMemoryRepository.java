package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractComputerRepository;

public interface IComputerMemoryRepository extends IAbstractComputerRepository<ComputerMemory, Long> {
}
