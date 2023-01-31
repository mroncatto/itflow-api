package io.github.mroncatto.itflow.domain.user.interfaces;

import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.config.exception.model.BadPasswordException;
import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IUserController extends IAbstractUserController<User> {
    public abstract ResponseEntity<User> save(User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    ResponseEntity<User> update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail;
    ResponseEntity<User> delete(String username) throws BadRequestException;
    ResponseEntity<List<Role>> findAllRoles();
    ResponseEntity<User> updateUserRoles(String username, List<Role> roles);
    ResponseEntity<User> updateProfile(User entity) throws AlreadExistingUserByEmail, BadRequestException;
    ResponseEntity<?> updateUserPassword(String oldPassword, String newPassword) throws BadPasswordException;
    ResponseEntity<?> resetUserPassword(String username);
    ResponseEntity<?> lockUnlockUser(String username);
    ResponseEntity<User> findUserByUsername(String username);
    ResponseEntity<Page<User>> findAll(int page, String param);
}
