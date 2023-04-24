package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractComputerRepository;

public interface IComputerStorageRepository extends IAbstractComputerRepository<ComputerStorage, Long> {
}
