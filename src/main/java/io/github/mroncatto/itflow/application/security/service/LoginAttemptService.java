package io.github.mroncatto.itflow.application.security.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginAttemptService {
    private final LoadingCache<String, Integer> loginAttemptCache;
    private final int attempt;

    public LoginAttemptService(@Value("${app.login.attempts}") int attemps) {
        this.attempt = attemps;
        this.loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).maximumSize(100)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void evictUserFromLoginAttemptCache(String username) {
        loginAttemptCache.invalidate(username);
    }

    public void addUserToLoginAttemptCache(String username) {
        int attempts = 0;
        try {
            attempts = 1 + loginAttemptCache.get(username);
        } catch (ExecutionException e) {
            log.error("Error logging in: {}", e.getMessage());
        }
        loginAttemptCache.put(username, attempts);
    }

    public boolean hasExceededMaxAttempts(String username) {
        try {
            return loginAttemptCache.get(username) >= attempt;
        } catch (ExecutionException e) {
            log.error("Error logging in: {}", e.getMessage());
        }
        return false;
    }
}
