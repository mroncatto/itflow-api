package io.github.mroncatto.itflow.config.constant;

public class SecurityConstant {
    public static final String BASE_URL = "/api/v1";
    public static final String AUTHENTICATION_URL = BASE_URL + "/auth/login";
    public static final String AUTHENTICATION_REFRESH_URL = BASE_URL + "/auth/refresh";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "TOKEN CANNOT BE AUTHORIZED";
    public static final String[] PUBLIC_URL = {"/", "/swagger-ui/**", "/v3/api-docs/**", AUTHENTICATION_REFRESH_URL};
    public static final String[] ALLOW_GET = {};
    public static final String[] ALLOW_POST = { BASE_URL + "/user/resetpassword" };
    public static final String[] ALLOW_CORS = { "http://localhost:4200", "http://localhost"};

}
