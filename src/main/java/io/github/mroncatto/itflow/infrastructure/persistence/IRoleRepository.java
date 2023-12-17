package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.user.entity.UserRole;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractUserRepository;

public interface IRoleRepository extends IAbstractUserRepository<UserRole, Long> {

}
