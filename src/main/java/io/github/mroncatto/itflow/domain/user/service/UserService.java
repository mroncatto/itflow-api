package io.github.mroncatto.itflow.domain.user.service;

import io.github.mroncatto.itflow.domain.user.abstracts.AbstractUserService;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static io.github.mroncatto.itflow.domain.commons.helper.DateHelper.currentDate;

@Service
public class UserService extends AbstractUserService{
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        super(userRepository, passwordEncoder);
        this.userRepository = userRepository;
    }

    public User login(String username){
        User user = this.userRepository.findUserByUsername(username);
        user.setLastLoginDate(currentDate());
        userRepository.save(user);
        return user;
    }
}
