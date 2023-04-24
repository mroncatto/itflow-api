package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.user.entity.Role;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractUserRepository;

public interface IRoleRepository extends IAbstractUserRepository<Role, Long> {

}
