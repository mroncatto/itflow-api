package io.github.mroncatto.itflow.domain.company.resource;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.company.interfaces.IDepartmentController;
import io.github.mroncatto.itflow.domain.company.model.Department;
import io.github.mroncatto.itflow.domain.company.service.DepartmentService;
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

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.List;

import static io.github.mroncatto.itflow.config.constant.ControllerConstant.PAGE_SIZE;
import static io.github.mroncatto.itflow.config.constant.SecurityConstant.BASE_URL;
import static io.github.mroncatto.itflow.domain.commons.helper.ApiResponseHelper.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = BASE_URL + "/department")
@Tag(name = "Company", description = "Companies, branches, and departments")
@RequiredArgsConstructor
public class DepartmentController implements IDepartmentController {
    private final DepartmentService departmentService;

    @Operation(summary = "Get all department", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, array = @ArraySchema(schema = @Schema(implementation = Department.class)))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    @Override
    public ResponseEntity<List<Department>> findAll() {
        return new ResponseEntity<>(this.departmentService.findAll(), OK);
    }

    @Operation(summary = "Get all department with pagination", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/page/{page}")
    @Override
    public ResponseEntity<Page<Department>> findAll(@PathVariable("page") int page) {
        return new ResponseEntity<>(this.departmentService.findAll(PageRequest.of(page, PAGE_SIZE)), OK);
    }

    @Operation(summary = "Create a new department", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response201, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = response400, description = badRequest, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Department> save(@Valid @RequestBody Department entity, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.departmentService.save(entity, result), CREATED);
    }

    @Operation(summary = "Update a specific department", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = response400, description = badRequest, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Department> update(@Valid @RequestBody Department entity, BindingResult result) throws BadRequestException, NoResultException {
        return new ResponseEntity<>(this.departmentService.update(entity, result), OK);
    }

    @Operation(summary = "Get department by ID", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Department> findById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.departmentService.findById(id), OK);
    }

    @Operation(summary = "Disable a department by ID", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Department> deleteById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.departmentService.deleteById(id), OK);
    }
}
