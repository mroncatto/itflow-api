package io.github.mroncatto.itflow.domain.user.interfaces;

import io.github.mroncatto.itflow.domain.user.model.Role;

import java.util.List;

public interface IRoleService {
    public List<Role> findAll();
}
