package io.github.mroncatto.itflow.security.model;

import io.github.mroncatto.itflow.domain.user.model.User;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("user")
public class TokenResponse {
    private String access_token;
    private String refresh_token;
    private User user;
}
