package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractComputerRepository;

public interface IComputerCpuRepository extends IAbstractComputerRepository<ComputerCpu, Long> {
}
