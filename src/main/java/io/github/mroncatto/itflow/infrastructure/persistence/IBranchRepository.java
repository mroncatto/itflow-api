package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.company.entity.Branch;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractCompanyRepository;

public interface IBranchRepository extends IAbstractCompanyRepository<Branch, Long> {
}
