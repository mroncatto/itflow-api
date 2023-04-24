package io.github.mroncatto.itflow.domain.user.service;

import io.github.mroncatto.itflow.application.security.filter.LoginAttemptService;
import io.github.mroncatto.itflow.application.security.jwt.JwtUserDetails;
import io.github.mroncatto.itflow.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findUserByUsername(username);
        validateLoginAttempt(user);
        return new JwtUserDetails(user);
    }

    private void validateLoginAttempt(User user) {
        if (user.isNonLocked()) {
            user.setNonLocked(!loginAttemptService.hasExceededMaxAttempts(user.getUsername()));
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
