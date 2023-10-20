package io.github.mroncatto.itflow.infrastructure.web.controller.device;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.DeviceRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceStaffRequestDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.model.IDeviceService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceStaffService;
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
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
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

import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.*;
import static io.github.mroncatto.itflow.domain.user.helper.RolesHelper.HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = EndpointUrlConstant.device)
@Tag(name = "Device", description = "Devices")
@RequiredArgsConstructor
public class DeviceController {
    private final IDeviceService service;
    private final IDeviceStaffService staffService;

    @Operation(summary = "Get all devices with pagination and filters", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Device.class)))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    public ResponseEntity<Page<Device>> findAll(
            @Conjunction(value = {
                    @Or({
                            @Spec(path = "id", params = "filter", spec = Equal.class),
                            @Spec(path = "code", params = "filter", spec = Equal.class),
                            @Spec(path = "tag", params = "filter", spec = LikeIgnoreCase.class),
                            @Spec(path = "hostname", params = "filter", spec = LikeIgnoreCase.class)
                    }),
            }, and = {
                    @Spec(path = "deviceCategory.id", params = "categories", spec = In.class, paramSeparator = ',', onTypeMismatch = OnTypeMismatch.IGNORE),
                    @Spec(path = "department.id", params = "departments", spec = In.class, paramSeparator = ',', onTypeMismatch = OnTypeMismatch.IGNORE)
            })
            Specification<Device> spec, @PageableDefault(size = 20) Pageable pageable) {
        return new ResponseEntity<>(this.service.findAll(spec, pageable), OK);
    }

    @Operation(summary = "Create a new device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> save(@RequestBody @Validated(DeviceRequestDto.DeviceView.DevicePost.class)
                                       @JsonView(DeviceRequestDto.DeviceView.DevicePost.class) DeviceRequestDto deviceRequestDto, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.service.save(deviceRequestDto, result), CREATED);
    }

    @Operation(summary = "Add or update an employee to a device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.staffId)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> updateStaff(@PathVariable("id") Long id,
                                              @RequestBody @Validated(DeviceStaffRequestDto.DeviceStaffView.DeviceStaffPut.class)
                                              @JsonView(DeviceStaffRequestDto.DeviceStaffView.DeviceStaffPut.class) DeviceStaffRequestDto deviceStaffRequestDto, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.staffService.updateStaff(deviceStaffRequestDto, id, result), CREATED);
    }

    @Operation(summary = "Update a specific device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_400, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> update(@RequestBody @Validated(DeviceRequestDto.DeviceView.DevicePut.class)
                                         @JsonView(DeviceRequestDto.DeviceView.DevicePut.class) DeviceRequestDto deviceRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        return new ResponseEntity<>(this.service.update(deviceRequestDto, result), OK);
    }

    @Operation(summary = "Get device by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.id)
    public ResponseEntity<Device> findById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.findById(id), OK);
    }

    @Operation(summary = "Disable a device by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.id)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> deleteById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.deleteById(id), OK);
    }

    @Operation(summary = "Remove employee from device by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.staffId)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> deleteStaffFromDevice(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.staffService.deleteStaffFromDevice(id), OK);
    }

}
