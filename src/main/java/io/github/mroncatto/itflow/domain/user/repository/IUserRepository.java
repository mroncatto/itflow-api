package io.github.mroncatto.itflow.domain.user.repository;

import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface IUserRepository extends IAbstractUserRepository<User, UUID>, JpaSpecificationExecutor<User> {

    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> findAllByEmail(String email);
}
