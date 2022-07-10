package io.github.mroncatto.itflow.domain.user.service;

import io.github.mroncatto.itflow.config.exception.model.*;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.user.interfaces.IUserService;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.distinct;
import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.match;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static io.github.mroncatto.itflow.domain.commons.helper.GeneratorHelper.generateRandomAlphanumeric;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.isNull;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Slf4j
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
        if (isNull(user))
            throw new UsernameNotFoundException("");
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = this.userRepository.findUserByEmail(email);
        if (isNull(user))
            throw new UserNotFoundException("");
        return user;
    }

    @Override
    @Transactional
    public User save(User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail {
        validateResult(result);
        validateExistingUsernameCreateUser(entity);
        validateExistingEmailCreateUser(entity);
        validateEmailField(entity.getEmail());
        String randomPassword = generateRandomAlphanumeric(6, false);
        entity.setPassword(passwordEncoder.encode(randomPassword));
        entity.setJoinDate(new Date());
        //TODO: Email service notification
        return this.userRepository.save(entity);
    }

    @Override
    public User update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail {
        validateResult(result);
        User user = this.findUserByUsername(username);
        validateExistingEmailUpdateUser(user, entity);
        validateEmailField(entity.getEmail());
        user.setFullName(entity.getFullName());
        user.setEmail(entity.getEmail());
        return this.userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        User user = this.findUserByUsername(username);
        user.setActive(false);
        this.userRepository.save(user);
    }

    @Override
    public User updateUserRoles(String username, List<Role> roles) {
        User user = this.findUserByUsername(username);
        user.setRole(roles);
        this.userRepository.save(user);
        return user;
    }

    @Override
    public User updateProfile(User entity) throws AlreadExistingUserByEmail, BadRequestException {
        String username = getContext().getAuthentication().getName();
        User user = this.findUserByUsername(username);
        validateExistingEmailUpdateUser(user, entity);
        validateNullFields(entity.getEmail(), entity.getFullName(), entity.getAvatar());
        validateEmptyFields(entity.getEmail(), entity.getFullName());
        validateEmailField(entity.getEmail());
        user.setEmail(entity.getEmail());
        user.setAvatar(entity.getAvatar());
        user.setFullName(entity.getFullName());
        return this.userRepository.save(user);
    }

    @Override
    public void updateUserPassword(String oldPassword, String newPassword) throws BadPasswordException {
        String username = getContext().getAuthentication().getName();
        User user = this.findUserByUsername(username);
        if (validateUserPassword(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            this.userRepository.save(user);
        } else {
            throw new BadPasswordException("");
        }
    }

    @Override
    @Transactional
    public void resetUserPassword(String username) {
        User user = this.findUserByUsername(username);
        String randomPassword = generateRandomAlphanumeric(6, false);
        user.setPassword(passwordEncoder.encode(randomPassword));
        //TODO: Email Notification Service
        //TODO: Improve this method to use tokens
        this.userRepository.save(user);
    }

    @Override
    public void unlockUser(String username) {
        User user = this.findUserByUsername(username);
        if(!user.isActive()) throw new UsernameNotFoundException("");
        user.setNonLocked(true);
        this.userRepository.save(user);
    }

    private void validateExistingUsernameCreateUser(User user) throws AlreadExistingUserByUsername {
        if (nonNull(this.userRepository.findUserByUsername(user.getUsername())))
            throw new AlreadExistingUserByUsername("");
    }

    private void validateExistingEmailCreateUser(User user) throws AlreadExistingUserByEmail {
        if (nonNull(this.userRepository.findUserByEmail(user.getEmail())))
            throw new AlreadExistingUserByEmail("");
    }

    private void validateExistingEmailUpdateUser(User user, User userDto) throws AlreadExistingUserByEmail {
        User userByEmail = this.userRepository.findUserByEmail(userDto.getEmail());
        if (nonNull(userByEmail)) {
            if(distinct(userByEmail.getId(), user.getId()) && match(userByEmail.getEmail(),userDto.getEmail()))
                throw new AlreadExistingUserByEmail("");
        }
    }

    private boolean validateUserPassword(String planePassword, String hashPassword) {
        return this.passwordEncoder.matches(planePassword, hashPassword);
    }
}
