package io.github.mroncatto.itflow.domain.user.service;

import io.github.mroncatto.itflow.domain.user.interfaces.IRoleService;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }
}
