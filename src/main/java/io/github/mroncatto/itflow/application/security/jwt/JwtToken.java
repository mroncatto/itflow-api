package io.github.mroncatto.itflow.application.security.jwt;

import io.github.mroncatto.itflow.domain.user.entity.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class JwtToken {
    private String access_token;
    private Date expire;
    private User user;
}
