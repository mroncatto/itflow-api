package io.github.mroncatto.itflow.domain.user.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.user.dto.UserProfileRequestDto;
import io.github.mroncatto.itflow.domain.user.dto.UserRequestDto;
import io.github.mroncatto.itflow.domain.user.entity.Role;
import io.github.mroncatto.itflow.domain.user.entity.User;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.domain.user.exception.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.domain.user.exception.BadPasswordException;
import io.github.mroncatto.itflow.domain.user.exception.UserNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface IUserService extends IAbstractUserService {

    User findUserById(UUID id) throws UserNotFoundException;
    User findUserByUsername(String username);
    User findUserByEmail(String email) throws UserNotFoundException;
    User save(UserRequestDto dto, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    User update(String username, UserRequestDto dto, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail, NoResultException;
    void delete(String username);
    User updateUserRoles(String username, List<Role> roles);
    User updateProfile(UserProfileRequestDto dto) throws AlreadExistingUserByEmail, BadRequestException;
    void updateUserPassword(String oldPassword, String newPassword) throws BadPasswordException;
    void resetUserPassword(String username);
    void lockUnlockUser(String username);

}
