package io.github.mroncatto.itflow.domain.user.repository;

import io.github.mroncatto.itflow.domain.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {

}
