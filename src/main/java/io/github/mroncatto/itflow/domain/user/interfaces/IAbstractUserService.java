package io.github.mroncatto.itflow.domain.user.interfaces;

import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

public interface IAbstractUserService {
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    User save(User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    User update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail, NoResultException;
}
