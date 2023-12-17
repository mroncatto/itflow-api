package io.github.mroncatto.itflow.infrastructure.web.controller.computer;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerCpuRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.domain.computer.model.IComputerCpuService;
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
import net.kaczmarzyk.spring.data.jpa.domain.IsTrue;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.github.mroncatto.itflow.application.config.constant.ControllerConstant.PAGE_SIZE;
import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.*;
import static io.github.mroncatto.itflow.domain.user.helper.RolesHelper.HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = EndpointUrlConstant.COMPUTER_CPU)
@Tag(name = "Computer", description = "Computer properties")
@RequiredArgsConstructor
public class ComputerCpuController {
    private final IComputerCpuService service;

    @Operation(summary = "Get all computer CPUs", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = ComputerCpu.class)))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    public ResponseEntity<List<ComputerCpu>> findAll() {
        return new ResponseEntity<>(this.service.findAll(), OK);
    }

    @Operation(summary = "Create a new computer category", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ComputerCpu.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<ComputerCpu> save(@RequestBody @Validated(ComputerCpuRequestDto.ComputerCpuView.ComputerCpuPost.class)
                                            @JsonView(ComputerCpuRequestDto.ComputerCpuView.ComputerCpuPost.class) ComputerCpuRequestDto computerCpuRequestDto, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.service.save(computerCpuRequestDto, result), CREATED);
    }

    @Operation(summary = "Update a specific computer cpu", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ComputerCpu.class))),
            @ApiResponse(responseCode = RESPONSE_400, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<ComputerCpu> update(@RequestBody @Validated(ComputerCpuRequestDto.ComputerCpuView.ComputerCpuPut.class)
                                              @JsonView(ComputerCpuRequestDto.ComputerCpuView.ComputerCpuPut.class) ComputerCpuRequestDto computerCpuRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        return new ResponseEntity<>(this.service.update(computerCpuRequestDto, result), OK);
    }

    @Operation(summary = "Get computer cpu by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ComputerCpu.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.ID)
    public ResponseEntity<ComputerCpu> findById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.findById(id), OK);
    }

    @Operation(summary = "Get all computer CPUs with pagination", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.PAGE)
    public ResponseEntity<Page<ComputerCpu>> findAll(@PathVariable("page") int page) {
        return new ResponseEntity<>(this.service.findAll(PageRequest.of(page, PAGE_SIZE)), OK);
    }

    @Operation(summary = "Disable a computer cpu by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ComputerCpu.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.ID)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<ComputerCpu> deleteById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.deleteById(id), OK);
    }

    @Operation(summary = "Get all computer cpu autocomplete filter", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = ComputerCpu.class)))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.AUTO_COMPLETE)
    public ResponseEntity<List<ComputerCpu>> findAll(
            @Conjunction(value = {
                    @Or({
                            @Spec(path = "id", params = "filter", spec = Equal.class),
                            @Spec(path = "brandName", params = "filter", spec = LikeIgnoreCase.class),
                    }),
            }, and = {
                    @Spec(path = "active", defaultVal = "true" ,spec = IsTrue.class)
            })
            Specification<ComputerCpu> spec) {
        return new ResponseEntity<>(this.service.findAll(spec), OK);
    }
}
