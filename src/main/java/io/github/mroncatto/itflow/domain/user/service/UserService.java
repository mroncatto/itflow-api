package io.github.mroncatto.itflow.domain.user.service;

import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.config.exception.model.UserNotFoundException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper;
import io.github.mroncatto.itflow.domain.user.interfaces.IUserService;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.notNull;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User> implements IUserService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User login(String username) {
        User user = this.userRepository.findUserByUsername(username);
        user.setLastLoginDate(currentDate());
        userRepository.save(user);
        return user;
    }
    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User findUserById(UUID id) throws UserNotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(""));
    }

    @Override
    public User findUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username);
        if(ValidationHelper.isNull(user))
            throw new UsernameNotFoundException("");
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = this.userRepository.findUserByEmail(email);
        if(ValidationHelper.isNull(user))
            throw new UserNotFoundException("");
        return user;
    }

    @Override
    public User save(User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail {
        validateResult(result);
        validateCreateOrUpdateUser(entity);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return this.userRepository.save(entity);
    }

    @Override
    public User update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail {
        validateResult(result);
        User user = this.findUserByUsername(username);
        validateCreateOrUpdateUser(user, entity);
        user.setFullName(entity.getFullName());
        user.setAvatar(entity.getAvatar());
        return this.userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        User user = this.findUserByUsername(username);
        user.setActive(false);
        user.setNonLocked(false);
        this.userRepository.save(user);
    }

    private void validateCreateOrUpdateUser(User user) throws AlreadExistingUserByUsername, AlreadExistingUserByEmail {
        if(notNull(this.userRepository.findUserByUsername(user.getUsername())))
            throw new AlreadExistingUserByUsername("");
        if(notNull(this.userRepository.findUserByEmail(user.getEmail())))
            throw new AlreadExistingUserByEmail("");
    }

    private void validateCreateOrUpdateUser(User user, User newUser) throws AlreadExistingUserByUsername, AlreadExistingUserByEmail {
        if(notNull(this.userRepository.findUserByUsername(user.getUsername())) && !user.getUsername().equals(newUser.getUsername()))
            throw new AlreadExistingUserByUsername("");
        if(notNull(this.userRepository.findUserByEmail(user.getEmail())) && !user.getEmail().equals(newUser.getEmail()))
            throw new AlreadExistingUserByEmail("");
    }
}
