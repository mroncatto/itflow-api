package io.github.mroncatto.itflow.domain.user.service;

import io.github.mroncatto.itflow.config.exception.model.*;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.commons.service.filter.FilterService;
import io.github.mroncatto.itflow.domain.email.service.EmailService;
import io.github.mroncatto.itflow.domain.user.interfaces.IUserService;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.distinct;
import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;
import static io.github.mroncatto.itflow.domain.commons.helper.GeneratorHelper.generateRandomAlphanumeric;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.isNull;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService implements IUserService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final FilterService filterService;

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
    public Page<User> findAll(Pageable pageable,
                              String filter) {

        return this.userRepository.findAll(
                (Specification<User>) (root, query, builder) -> {

                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(filterService.equalsFilter(builder,root,"active", true));

                    if (nonNull(filter)){
                        Predicate predicateFullName = filterService.likeFilter(builder, root,"fullName", filter);
                        Predicate predicateUsername = filterService.likeFilter(builder, root,"username", filter);
                        Predicate predicateEmail = filterService.likeFilter(builder, root,"email", filter);
                        predicates.add(builder.or(predicateFullName, predicateUsername, predicateEmail));
                    }

                    return builder.and(predicates.toArray(Predicate[]::new));

                }, pageable);
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

    public User findUserByUsernameActiveOnly(String username) {
        User user = this.findUserByUsername(username);
        if (!user.isActive()) throw new UsernameNotFoundException("USER NOT FOUND OR INACTIVE");
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
        validateUniqueUsername(entity);
        validateUniqueEmail(entity);
        validateEmailField(entity.getEmail());
        String randomPassword = generateRandomAlphanumeric(6, false);
        entity.setPassword(passwordEncoder.encode(randomPassword));
        entity.setPassword_expired(true);
        entity.setJoinDate(new Date());
        this.emailService.welcome(entity, randomPassword);
        return this.userRepository.save(entity);
    }

    @Override
    public User update(String username, User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail {
        validateResult(result);
        User user = this.findUserByUsername(username);
        validateUniqueEmail(entity);
        validateEmailField(entity.getEmail());
        user.setFullName(entity.getFullName());
        user.setEmail(entity.getEmail());
        user.setStaff(entity.getStaff());
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
        User user = this.findUserByUsernameActiveOnly(username);
        user.setRole(roles);
        this.userRepository.save(user);
        return user;
    }

    @Override
    public User updateProfile(User entity) throws AlreadExistingUserByEmail, BadRequestException {
        String username = getContext().getAuthentication().getName();
        User user = this.findUserByUsername(username);
        validateUniqueEmail(entity);
        validateNullFields(entity.getEmail(), entity.getFullName());
        validateEmptyFields(entity.getEmail(), entity.getFullName());
        validateEmailField(entity.getEmail());
        user.setEmail(entity.getEmail());
        user.setFullName(entity.getFullName());
        return this.userRepository.save(user);
    }

    @Override
    public void updateUserPassword(String oldPassword, String newPassword) throws BadPasswordException {
        String username = getContext().getAuthentication().getName();
        User user = this.findUserByUsername(username);
        if (validateUserPassword(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setPassword_expired(false);
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
        user.setPassword_expired(true);
        this.emailService.resetPassword(user, randomPassword);
        //TODO: Improve this method to use tokens
        this.userRepository.save(user);
    }

    @Override
    public void lockUnlockUser(String username) {
        User user = this.findUserByUsername(username);
        if (!user.isActive()) throw new UsernameNotFoundException("");
        user.setNonLocked(!user.isNonLocked());
        this.userRepository.save(user);
    }

    private void validateUniqueEmail(User user) throws AlreadExistingUserByEmail {
        User anyuser = this.userRepository.findAllByEmail(user.getEmail())
                .stream()
                .filter(User::isActive)
                .findFirst()
                .orElse(null);

        if (nonNull(anyuser) && distinct(anyuser.getUsername(), user.getUsername()))
            throw new AlreadExistingUserByEmail("");
}

    private void validateUniqueUsername(User user) throws AlreadExistingUserByUsername {
        if (nonNull(this.userRepository.findUserByUsername(user.getUsername())))
            throw new AlreadExistingUserByUsername("");
    }


    private boolean validateUserPassword(String planePassword, String hashPassword) {
        return this.passwordEncoder.matches(planePassword, hashPassword);
    }
}
