package io.github.mroncatto.itflow.domain.user.resource;

import io.github.mroncatto.itflow.config.constant.SecurityConstant;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.security.model.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
public class AuthController {

    @Operation(summary = "Get Authentication Token", responses = {
            @ApiResponse(description = "Successful", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "403", description = "Bad Credentials / Blocked Account / Invalid account", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomHttpResponse.class)))) })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(SecurityConstant.AUTHENTICATION_URL)
    private void login(@RequestParam String username, @RequestParam String password) {
        // Provide by AuthenticationFilter
    }
}
