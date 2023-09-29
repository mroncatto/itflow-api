package io.github.mroncatto.itflow.application.security.jwt;

import io.github.mroncatto.itflow.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class JwtTokenDto {
    private String access_token;
    private Date expire;
    private User user;
}
