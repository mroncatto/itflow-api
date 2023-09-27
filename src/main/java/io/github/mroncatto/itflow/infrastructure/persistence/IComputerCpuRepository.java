package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractComputerRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IComputerCpuRepository extends IAbstractComputerRepository<ComputerCpu, Long>, JpaSpecificationExecutor<ComputerCpu> {
}
