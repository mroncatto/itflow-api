package io.github.mroncatto.itflow.domain.user.model;

import io.github.mroncatto.itflow.domain.user.dto.UserRequestDto;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

public interface IAbstractUserService {
    List<User> findAll();
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    User save(UserRequestDto dto, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    User update(String username, UserRequestDto dto, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail, NoResultException;
}
