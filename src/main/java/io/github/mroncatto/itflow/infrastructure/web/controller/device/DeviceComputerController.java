package io.github.mroncatto.itflow.infrastructure.web.controller.device;

import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerCpu;
import io.github.mroncatto.itflow.domain.device.model.IDeviceComputerController;
import io.github.mroncatto.itflow.domain.device.service.DeviceService;
import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.*;
import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.APPLICATION_JSON;
import static io.github.mroncatto.itflow.domain.user.helper.RolesHelper.HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = EndpointUrlConstant.device)
@Tag(name = "Device", description = "Devices")
@RequiredArgsConstructor
public class DeviceComputerController implements IDeviceComputerController {
    private final DeviceService service;

    @Operation(summary = "Add or update an computer to a device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.computerId)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    @Override
    public ResponseEntity<Device> updateComputer(@PathVariable("id") Long id, @Valid @RequestBody DeviceComputer entity, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.service.updateComputer(entity, id, result), CREATED);
    }

    @Operation(summary = "Remove computer from device by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.computerId)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    @Override
    public ResponseEntity<Device> deleteComputerFromDevice(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.deleteComputerFromDevice(id), OK);
    }

    @Operation(summary = "Add or update an computer to a device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = DeviceComputerCpu.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.deviceComputerCpu)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    @Override
    public ResponseEntity<Device> addDeviceComputerCpu(@PathVariable("id") Long id, @Valid @RequestBody DeviceComputerCpu entity, BindingResult result) throws NoResultException, BadRequestException {
        return new ResponseEntity<>(this.service.addDeviceComputerCpu(entity, id, result), OK);
    }

}