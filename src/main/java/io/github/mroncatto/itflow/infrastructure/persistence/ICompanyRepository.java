package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.company.entity.Company;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractCompanyRepository;

public interface ICompanyRepository extends IAbstractCompanyRepository<Company, Long> {
}
