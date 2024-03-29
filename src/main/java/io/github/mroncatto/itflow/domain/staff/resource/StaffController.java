package io.github.mroncatto.itflow.domain.staff.resource;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.staff.interfaces.IStaffController;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
import io.github.mroncatto.itflow.domain.staff.service.StaffService;
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
@RequestMapping(value = BASE_URL + "/staff")
@Tag(name = "Staff", description = "Employees, customers and stakeholders.")
@RequiredArgsConstructor
public class StaffController implements IStaffController {
    private final StaffService staffService;

    @Operation(summary = "Get all workers", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, array = @ArraySchema(schema = @Schema(implementation = Staff.class)))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    @Override
    public ResponseEntity<List<Staff>> findAll() {
        return new ResponseEntity<>(this.staffService.findAll(), OK);
    }

    @Operation(summary = "Get all workers with pagination", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/page/{page}")
    @Override
    public ResponseEntity<Page<Staff>> findAll(@PathVariable("page") int page) {
        return new ResponseEntity<>(this.staffService.findAll(PageRequest.of(page, PAGE_SIZE)), OK);
    }

    @Operation(summary = "Create a new worker", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response201, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Staff.class))),
            @ApiResponse(responseCode = response400, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Staff> save(@Valid @RequestBody Staff entity, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.staffService.save(entity, result), CREATED);
    }

    @Operation(summary = "Update a specific worker", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Staff.class))),
            @ApiResponse(responseCode = response400, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Staff> update(@Valid @RequestBody Staff entity, BindingResult result) throws BadRequestException, NoResultException {
        return new ResponseEntity<>(this.staffService.update(entity, result), OK);
    }

    @Operation(summary = "Get worker by UUID", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Staff.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/{uuid}")
    @Override
    public ResponseEntity<Staff> findById(@PathVariable("uuid") String id) throws NoResultException {
        return new ResponseEntity<>(this.staffService.findById(id.toString()), OK);
    }

    @Operation(summary = "Delete worker by UUID", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Staff.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping("/{uuid}")
    @Override
    public ResponseEntity<Staff> deleteById(@PathVariable("uuid") String id) throws NoResultException {
        return new ResponseEntity<>(this.staffService.deleteById(id), OK);
    }
}
