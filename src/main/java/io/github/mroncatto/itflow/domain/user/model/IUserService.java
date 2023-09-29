package io.github.mroncatto.itflow.domain.user.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.domain.user.exception.BadPasswordException;
import io.github.mroncatto.itflow.domain.user.exception.UserNotFoundException;
import io.github.mroncatto.itflow.domain.user.entity.Role;
import io.github.mroncatto.itflow.domain.user.entity.User;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

public interface IUserService extends IAbstractUserService {

    User findUserById(UUID id) throws UserNotFoundException;
    User findUserByUsername(String username);
    User findUserByEmail(String email) throws UserNotFoundException;
    User save(User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    User update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail, NoResultException;
    void delete(String username);
    User updateUserRoles(String username, List<Role> roles);
    User updateProfile(User entity) throws AlreadExistingUserByEmail, BadRequestException;
    void updateUserPassword(String oldPassword, String newPassword) throws BadPasswordException;
    void resetUserPassword(String username);
    void lockUnlockUser(String username);

}
