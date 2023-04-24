package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractComputerRepository;

public interface IComputerCategoryRepository extends IAbstractComputerRepository<ComputerCategory, Long> {
}
