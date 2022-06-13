package io.github.mroncatto.itflow.domain.user.abstracts;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.config.exception.model.UserNotFoundException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper;
import io.github.mroncatto.itflow.domain.interfaces.IEntityService;
import io.github.mroncatto.itflow.domain.user.interfaces.IUserService;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.List;

@AllArgsConstructor
public class AbstractUserService extends AbstractService implements IUserService, IEntityService<User> {
    protected final IUserRepository userRepository;
    protected final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User>findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User findUserById(Long id) throws UserNotFoundException {
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
    public User save(User entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        //TODO: Falta validacao para criar email e username!!
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return this.userRepository.save(entity);
    }

    @Override
    public User update(String username, User entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        User user = this.findUserByUsername(username);
        //TODO: Falta validacao para alterar email e username!!
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
}
