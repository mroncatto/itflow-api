package io.github.mroncatto.itflow.infrastructure.web.controller.staff;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.staff.dto.StaffRequestDto;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import io.github.mroncatto.itflow.domain.staff.model.IStaffService;
import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.IsTrue;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.OnTypeMismatch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.*;
import static io.github.mroncatto.itflow.domain.user.helper.RolesHelper.HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = EndpointUrlConstant.STAFF)
@Tag(name = "Staff", description = "Employees, customers and stakeholders.")
@RequiredArgsConstructor
public class StaffController {
    private final IStaffService staffService;

    @Operation(summary = "Get all workers", security = {@SecurityRequirement(name = BEARER_AUTH)}, responses = {@ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Staff.class)))), @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.ALL)
    public ResponseEntity<List<Staff>> findAll() {
        return new ResponseEntity<>(this.staffService.findAll(), OK);
    }

    @Operation(summary = "Get all workers", security = {@SecurityRequirement(name = BEARER_AUTH)}, responses = {@ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Staff.class)))), @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    public ResponseEntity<Page<Staff>> findAll(
            @Conjunction(value = {
                    @Or({
                            @Spec(path = "email", params = "filter", spec = LikeIgnoreCase.class),
                            @Spec(path = "fullName", params = "filter", spec = LikeIgnoreCase.class)
                    }),
            }, and = {
                    @Spec(path = "occupation.id", params = "occupations", spec = In.class, paramSeparator = ',', onTypeMismatch = OnTypeMismatch.IGNORE),
                    @Spec(path = "department.id", params = "departments", spec = In.class, paramSeparator = ',', onTypeMismatch = OnTypeMismatch.IGNORE),
                    @Spec(path = "active", defaultVal = "true" ,spec = IsTrue.class)
            })
            Specification<Staff> spec, @PageableDefault(size = 20) Pageable pageable) {
        return new ResponseEntity<>(this.staffService.findAll(spec, pageable), OK);
    }

    @Operation(summary = "Create a new worker", security = {@SecurityRequirement(name = BEARER_AUTH)}, responses = {@ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Staff.class))), @ApiResponse(responseCode = RESPONSE_400, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))), @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Staff> save(@RequestBody @Validated(StaffRequestDto.StaffView.StaffPost.class) @JsonView(StaffRequestDto.StaffView.StaffPost.class) StaffRequestDto staffRequestDto, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.staffService.save(staffRequestDto, result), CREATED);
    }

    @Operation(summary = "Update a specific worker", security = {@SecurityRequirement(name = BEARER_AUTH)}, responses = {@ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Staff.class))), @ApiResponse(responseCode = RESPONSE_400, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))), @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))), @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Staff> update(@RequestBody @Validated(StaffRequestDto.StaffView.StaffPut.class) @JsonView(StaffRequestDto.StaffView.StaffPut.class) StaffRequestDto staffRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        return new ResponseEntity<>(this.staffService.update(staffRequestDto, result), OK);
    }

    @Operation(summary = "Get worker by UUID", security = {@SecurityRequirement(name = BEARER_AUTH)}, responses = {@ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Staff.class))), @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))), @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.UUID)
    public ResponseEntity<Staff> findById(@PathVariable("uuid") String id) throws NoResultException {
        return new ResponseEntity<>(this.staffService.findById(id), OK);
    }

    @Operation(summary = "Delete worker by UUID", security = {@SecurityRequirement(name = BEARER_AUTH)}, responses = {@ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Staff.class))), @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))), @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.UUID)
    public ResponseEntity<Staff> deleteById(@PathVariable("uuid") String id) throws NoResultException {
        return new ResponseEntity<>(this.staffService.deleteById(id), OK);
    }
}
