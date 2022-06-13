package io.github.mroncatto.itflow.domain.user.repository;

import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
