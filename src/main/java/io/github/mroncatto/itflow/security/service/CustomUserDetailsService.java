package io.github.mroncatto.itflow.security.service;

import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.service.UserService;
import io.github.mroncatto.itflow.security.model.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findUserByUsername(username);
        validateLoginAttempt(user);
        return new UserPrincipal(user);
    }

    private void validateLoginAttempt(User user) {
        if (user.isNonLocked()) {
            user.setNonLocked(!loginAttemptService.hasExceededMaxAttempts(user.getUsername()));
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
