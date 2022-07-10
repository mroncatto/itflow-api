package io.github.mroncatto.itflow.domain.user.resource;

import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByEmail;
import io.github.mroncatto.itflow.config.exception.model.AlreadExistingUserByUsername;
import io.github.mroncatto.itflow.config.exception.model.BadPasswordException;
import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractController;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.user.interfaces.IUserController;
import io.github.mroncatto.itflow.domain.user.model.Role;
import io.github.mroncatto.itflow.domain.user.model.User;
import io.github.mroncatto.itflow.domain.user.service.RoleService;
import io.github.mroncatto.itflow.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController extends AbstractController<User> implements IUserController {
    private final UserService userService;
    private final RoleService roleService;

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
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<User> save(@Valid @RequestBody User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByUsername, AlreadExistingUserByEmail {
        return new ResponseEntity<>(this.userService.save(entity, result), CREATED);
    }

    @Operation(summary = "Update a specific user account", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping("/{username}")
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<User> update(@PathVariable("username") String username, @Valid @RequestBody User entity, BindingResult result) throws BadRequestException, AlreadExistingUserByEmail {
        return new ResponseEntity<>(this.userService.update(username, entity, result), OK);
    }

    @Operation(summary = "Inactive a specific user account", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping("/{username}")
    @PreAuthorize("hasAnyAuthority({'COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<User> delete(@PathVariable("username") String username) throws BadRequestException {
        this.userService.delete(username);
        return new ResponseEntity<>(null, OK);
    }

    @Operation(summary = "Get all roles", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Role.class)))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/role")
    @PreAuthorize("hasAnyAuthority({'COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<List<Role>> findAllRoles() {
        return new ResponseEntity<>(this.roleService.findAll(), OK);
    }

    @Operation(summary = "Update user roles", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping("/{username}/role")
    @PreAuthorize("hasAnyAuthority({'MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<User> updateUserRoles(@PathVariable("username") String username, @RequestBody List<Role> roles) {
        return new ResponseEntity<>(this.userService.updateUserRoles(username, roles), OK);
    }

    @Operation(summary = "Update user profile", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping("/profile")
    @Override
    public ResponseEntity<User> updateProfile(@RequestBody User entity) throws AlreadExistingUserByEmail, BadRequestException {
        return new ResponseEntity<>(this.userService.updateProfile(entity), OK);
    }

    @Operation(summary = "Update user password with old password", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping("/updatepassword")
    @Override
    public ResponseEntity<?> updateUserPassword(@RequestParam("oldPassword") String oldPassword,
                                                @RequestParam("newPassword") String newPassword) throws BadPasswordException {
        this.userService.updateUserPassword(oldPassword, newPassword);
        return new ResponseEntity<>(null, OK);
    }

    @Operation(summary = "Reset user password", responses = {@ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PostMapping("/resetpassword")
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<?> resetUserPassword(@RequestParam("username") String username) {
        this.userService.resetUserPassword(username);
        return new ResponseEntity<>(null, OK);
    }

    @Operation(summary = "Unlock a specific user account", security = {
            @SecurityRequirement(name = "bearerAuth")}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping("/unlock/{username}")
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<?> unlockUser(@PathVariable("username") String username) {
        this.userService.unlockUser(username);
        return new ResponseEntity<>(null, OK);
    }
}
