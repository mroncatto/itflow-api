package io.github.mroncatto.itflow.infrastructure.web.controller.company;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.company.dto.CompanyRequestDto;
import io.github.mroncatto.itflow.domain.company.entity.Company;
import io.github.mroncatto.itflow.domain.company.model.ICompanyService;
import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import io.github.mroncatto.itflow.infrastructure.web.advice.CustomHttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.github.mroncatto.itflow.application.config.constant.ControllerConstant.PAGE_SIZE;
import static io.github.mroncatto.itflow.domain.commons.helper.SwaggerPropertiesHelper.*;
import static io.github.mroncatto.itflow.domain.user.helper.RolesHelper.HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN;
import static io.github.mroncatto.itflow.domain.user.helper.RolesHelper.MANAGER_OR_ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Log4j2
@RequestMapping(value = EndpointUrlConstant.COMPANY)
@Tag(name = "Company", description = "Companies, branches, and departments")
@RequiredArgsConstructor
public class CompanyController {
    private final ICompanyService companyService;

    @Operation(summary = "Get all companies", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = Company.class)))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    public ResponseEntity<List<Company>> findAll() {
        return new ResponseEntity<>(this.companyService.findAll(), OK);
    }


    @Operation(summary = "Get all companies with pagination", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.PAGE)
    public ResponseEntity<Page<Company>> findAll(@PathVariable("page") int page, @RequestParam(required = false, name = "filter") String filter) {
        return new ResponseEntity<>(this.companyService.findAll(PageRequest.of(page, PAGE_SIZE), filter), OK);
    }

    @Operation(summary = "Create a new company", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_201, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = RESPONSE_400, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Company> save(@RequestBody @Validated(CompanyRequestDto.CompanyView.CompanyPost.class)
                                        @JsonView(CompanyRequestDto.CompanyView.CompanyPost.class) CompanyRequestDto dto, BindingResult result) throws BadRequestException {
        log.debug("POST save CompanyDto: {}", dto.getName());
        return new ResponseEntity<>(this.companyService.save(dto, result), CREATED);
    }

    @Operation(summary = "Update a specific company", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = RESPONSE_400, description = BAD_REQUEST, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<Company> update(@Valid @RequestBody @Validated(CompanyRequestDto.CompanyView.CompanyPut.class)
                                              @JsonView(CompanyRequestDto.CompanyView.CompanyPut.class) CompanyRequestDto dto, BindingResult result) throws BadRequestException, NoResultException {
        log.debug("PUT update CompanyDto: {}", dto.getName());
        return new ResponseEntity<>(this.companyService.update(dto, result), OK);
    }

    @Operation(summary = "Get company by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.ID)
    public ResponseEntity<Company> findById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.companyService.findById(id), OK);
    }

    @Operation(summary = "Disable a company by ID", security = {
            @SecurityRequirement(name = BEARER_AUTH)}, responses = {
            @ApiResponse(responseCode = RESPONSE_200, description = SUCCESSFUL, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Occupation.class))),
            @ApiResponse(responseCode = RESPONSE_404, description = NOT_FOUND, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = RESPONSE_401, description = UNAUTHORIZED, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.ID)
    @PreAuthorize(MANAGER_OR_ADMIN)
    public ResponseEntity<Company> deleteById(@PathVariable("id") Long id) throws NoResultException {
        log.debug("DELETE disable CompanyDto by ID: {}", id);
        return new ResponseEntity<>(this.companyService.deleteById(id), OK);
    }


}
