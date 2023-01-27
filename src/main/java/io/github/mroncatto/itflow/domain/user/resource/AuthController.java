package io.github.mroncatto.itflow.domain.user.resource;

import io.github.mroncatto.itflow.config.constant.SecurityConstant;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.user.service.UserService;
import io.github.mroncatto.itflow.security.model.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.*;

@Tag(name = "User", description = "User accounts")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService service;

    @Operation(summary = "Get Authentication Token", responses = {
            @ApiResponse(description = SUCCESSFUL, responseCode = RESPONSE_200, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = RESPONSE_403, description = "Bad Credentials / Blocked Account / Invalid account", content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = CustomHttpResponse.class)))) })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(SecurityConstant.AUTHENTICATION_URL)
    private void login(@RequestParam String username, @RequestParam String password) {
        // Provide by AuthenticationFilter
    }

    @Operation(summary = "Refresh User Token", responses = {
            @ApiResponse(description = SUCCESSFUL, responseCode = RESPONSE_200, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = RESPONSE_403, description = "Bad Token / Token Expired ", content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = CustomHttpResponse.class)))) })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(SecurityConstant.AUTHENTICATION_REFRESH_URL)
    private ResponseEntity<?> refresh(@RequestParam String refresh_token) throws AuthenticationException {
        return new ResponseEntity<>(this.service.refreshToken(refresh_token), HttpStatus.OK);
    }
}
