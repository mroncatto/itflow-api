package io.github.mroncatto.itflow.infrastructure.web.controller.device;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.Nulls;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.*;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerMemory;
import io.github.mroncatto.itflow.domain.device.model.IDeviceComputerService;
import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = EndpointUrlConstant.DEVICE)
@Tag(name = "Device", description = "Devices")
@RequiredArgsConstructor
public class DeviceComputerController {
    private final IDeviceComputerService service;

    @Operation(summary = "Add or update an computer to a device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.COMPUTER_ID)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> updateComputer(@PathVariable("id") Long id,
                                                 @RequestBody @Validated(DeviceComputerRequestDto.DeviceComputerView.DeviceComputerPut.class)
                                                 @JsonView(DeviceComputerRequestDto.DeviceComputerView.DeviceComputerPut.class) DeviceComputerRequestDto deviceComputerRequestDto, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.service.updateComputer(deviceComputerRequestDto, id, result), CREATED);
    }

    @Operation(summary = "Remove computer from device by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.COMPUTER_ID)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> deleteComputerFromDevice(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.deleteComputerFromDevice(id), OK);
    }

    @Operation(summary = "Add or update an computer cpu to a computer device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = DeviceComputerCpu.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.DEVICE_COMPUTER_CPU)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> addDeviceComputerCpu(@PathVariable("id") Long id,
                                                       @RequestBody @Validated(DeviceComputerCpuRequestDto.DeviceComputerCpuView.DeviceComputerCpuPut.class)
                                                        @JsonView(DeviceComputerCpuRequestDto.DeviceComputerCpuView.DeviceComputerCpuPut.class) DeviceComputerCpuRequestDto deviceComputerCpuRequestDto, BindingResult result) throws NoResultException, BadRequestException {
        return new ResponseEntity<>(this.service.addDeviceComputerCpu(deviceComputerCpuRequestDto, id, result), OK);
    }

    @Operation(summary = "Remove a computer cpu from a computer device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Nulls.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.DEVICE_COMPUTER_CPU_DELETE)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Object> removeDeviceComputerCpu(@PathVariable("id") Long id, @PathVariable("cpuId") Long cpuId) throws NoResultException, BadRequestException {
        this.service.deleteDeviceComputerCpu(id, cpuId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Add or update an computer memory to a computer device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = DeviceComputerMemory.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.DEVICE_COMPUTER_MEMORY)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> addDeviceComputerMemory(@PathVariable("id") Long id,
                                                       @RequestBody @Validated(DeviceComputerMemoryRequestDto.DeviceComputerMemoryView.DeviceComputerMemoryPut.class)
                                                       @JsonView(DeviceComputerMemoryRequestDto.DeviceComputerMemoryView.DeviceComputerMemoryPut.class) DeviceComputerMemoryRequestDto deviceComputerMemoryRequestDto, BindingResult result) throws NoResultException, BadRequestException {
        return new ResponseEntity<>(this.service.addDeviceComputerMemory(deviceComputerMemoryRequestDto, id, result), OK);
    }

    @Operation(summary = "Remove a computer memory from a computer device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Nulls.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.DEVICE_COMPUTER_MEMORY_DELETE)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Object> removeDeviceComputerMemory(@PathVariable("id") Long id, @PathVariable("memoryId") Long memoryId) throws NoResultException, BadRequestException {
        this.service.deleteDeviceComputerMemory(id, memoryId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Add or update an computer storage to a computer device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = DeviceComputerMemory.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.DEVICE_COMPUTER_STORAGE)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> addDeviceComputerStorage(@PathVariable("id") Long id,
                                                          @RequestBody @Validated(DeviceComputerStorageRequestDto.DeviceComputerStorageView.DeviceComputerStoragePut.class)
                                                          @JsonView(DeviceComputerStorageRequestDto.DeviceComputerStorageView.DeviceComputerStoragePut.class) DeviceComputerStorageRequestDto deviceComputerStorageRequestDto, BindingResult result) throws NoResultException, BadRequestException {
        return new ResponseEntity<>(this.service.addDeviceComputerStorage(deviceComputerStorageRequestDto, id, result), OK);
    }

    @Operation(summary = "Remove a computer storage from a computer device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Nulls.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.DEVICE_COMPUTER_STORAGE_DELETE)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Object> removeDeviceComputerStorage(@PathVariable("id") Long id, @PathVariable("storageId") Long storageId) throws NoResultException, BadRequestException {
        this.service.deleteDeviceComputerStorage(id, storageId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "Add or update an computer software to a computer device", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = DeviceComputerMemory.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PutMapping(EndpointUrlConstant.DEVICE_COMPUTER_SOFTWARE)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Device> addDeviceComputerSoftware(@PathVariable("id") Long id,
                                                             @RequestBody @Validated(DeviceComputerSoftwareRequestDto.DeviceComputerSoftwareView.DeviceComputerSoftwarePost.class)
                                                           @JsonView(DeviceComputerSoftwareRequestDto.DeviceComputerSoftwareView.DeviceComputerSoftwarePost.class) DeviceComputerSoftwareRequestDto computerSoftwareRequestDto, BindingResult result) throws NoResultException, BadRequestException {
        return new ResponseEntity<>(this.service.addDeviceComputerSoftware(computerSoftwareRequestDto, id, result), OK);
    }



}
