package io.github.mroncatto.itflow.infrastructure.web.controller;

import io.github.mroncatto.itflow.application.config.constant.SecurityConstant;
import io.github.mroncatto.itflow.infrastructure.web.controller.advice.CustomHttpResponse;
import io.github.mroncatto.itflow.application.security.jwt.JwtToken;
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

import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.*;

@Tag(name = "User", description = "User accounts")
@RestController
public class AuthController {

    @Operation(summary = "Get Authentication Token", responses = {
            @ApiResponse(description = SUCCESSFUL, responseCode = RESPONSE_200, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = JwtToken.class))),
            @ApiResponse(responseCode = RESPONSE_403, description = "Bad Credentials / Blocked Account / Invalid account", content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = CustomHttpResponse.class)))) })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(SecurityConstant.AUTHENTICATION_URL)
    private void login(@RequestParam String username, @RequestParam String password) {
        // Provide by AuthenticationFilter
    }
}
