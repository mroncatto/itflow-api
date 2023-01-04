package io.github.mroncatto.itflow.domain.user.interfaces;

import io.github.mroncatto.itflow.config.exception.model.*;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
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
