package io.github.mroncatto.itflow.domain.user.repository;

import io.github.mroncatto.itflow.domain.user.model.User;

import java.util.UUID;

public interface IUserRepository extends IAbstractUserRepository<User, UUID> {

    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
