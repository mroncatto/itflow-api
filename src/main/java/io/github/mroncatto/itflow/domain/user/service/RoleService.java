package io.github.mroncatto.itflow.domain.user.service;

import io.github.mroncatto.itflow.domain.user.model.IRoleService;
import io.github.mroncatto.itflow.domain.user.entity.UserRole;
import io.github.mroncatto.itflow.infrastructure.persistence.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;
    @Override
    @Cacheable(value = "Roles", key = "#root.method.name")
    public List<UserRole> findAll() {
        return this.roleRepository.findAll();
    }
}
