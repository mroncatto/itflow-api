package io.github.mroncatto.itflow.domain.user.interfaces;

import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IUserController {
    ResponseEntity<User> update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail;
    ResponseEntity<User> delete(String username);
}
