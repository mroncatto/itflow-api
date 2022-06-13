package io.github.mroncatto.itflow.domain.user.abstracts;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractController;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.interfaces.IEntityController;
import io.github.mroncatto.itflow.domain.user.interfaces.IUserController;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static io.github.mroncatto.itflow.config.constant.SecurityConstant.BASE_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = BASE_URL + "/user")
@Tag(name = "User")
@AllArgsConstructor
public class AbstractUserController extends AbstractController implements IUserController, IEntityController<User> {
    private final UserService userService;

    @Operation(summary = "Get all users", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    @Override
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(this.userService.findAll(), OK);
    }

    @Operation(summary = "Get all users with pagination", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/page/{page}")
    @Override
    public ResponseEntity<Page<User>> findAll(@PathVariable("page") int page) {
        return new ResponseEntity<>(this.userService.findAll(PageRequest.of(page, 10)), OK);
    }

    @Operation(summary = "Create a new user account", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public ResponseEntity<User> save(@Valid @RequestBody User entity, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.userService.save(entity, result), CREATED);
    }

    @Operation(summary = "Update a specific user account", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public ResponseEntity<User> update(@PathVariable("username") String username, @Valid @RequestBody User entity, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.userService.update(username, entity, result), OK);
    }

    @Override
    public ResponseEntity<User> delete(String username, User entity, BindingResult result) {
        return null;
    }
}
