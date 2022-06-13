package io.github.mroncatto.itflow.domain.user.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.config.exception.model.UserNotFoundException;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.springframework.validation.BindingResult;

public interface IUserService {
    User findUserByUsername(String username);
    User findUserByEmail(String email) throws UserNotFoundException;

    User update(String username, User entity, BindingResult result) throws BadRequestException;

    void delete(String username);

}
