package io.github.mroncatto.itflow.domain.user.model;

import io.github.mroncatto.itflow.domain.user.entity.UserRole;

import java.util.List;

public interface IRoleService {
    public List<UserRole> findAll();
}
