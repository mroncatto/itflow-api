package io.github.mroncatto.itflow.domain.user.interfaces;

import io.github.mroncatto.itflow.config.exception.model.*;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User findUserById(UUID id) throws UserNotFoundException;
    User findUserByUsername(String username);
    User findUserByEmail(String email) throws UserNotFoundException;
    User update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail;
    void delete(String username) throws BadRequestException;
    User updateUserRoles(String username, List<Role> roles);
    User updateProfile(User entity) throws AlreadExistingUserByEmail, BadRequestException;
    void updateUserPassword(String oldPassword, String newPassword) throws BadPasswordException;
    void resetUserPassword(String username);

    void unlockUser(String username);

}
