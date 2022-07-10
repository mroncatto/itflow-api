package io.github.mroncatto.itflow.security.model;

import io.github.mroncatto.itflow.domain.user.model.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class TokenResponse {
    private String access_token;
    private Date expire;
    private User user;
}
