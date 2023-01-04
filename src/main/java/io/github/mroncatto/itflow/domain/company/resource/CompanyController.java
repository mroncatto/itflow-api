package io.github.mroncatto.itflow.domain.company.resource;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.commons.model.CustomHttpResponse;
import io.github.mroncatto.itflow.domain.company.interfaces.ICompanyController;
import io.github.mroncatto.itflow.domain.company.model.Company;
import io.github.mroncatto.itflow.domain.company.service.CompanyService;
import io.github.mroncatto.itflow.domain.staff.model.Occupation;
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
@RequestMapping(value = BASE_URL + "/company")
@Tag(name = "Company", description = "Companies, branches, and departments")
@RequiredArgsConstructor
public class CompanyController implements ICompanyController {
    private final CompanyService companyService;

    @Operation(summary = "Get all companies", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, array = @ArraySchema(schema = @Schema(implementation = Company.class)))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping()
    @Override
    public ResponseEntity<List<Company>> findAll() {
        return new ResponseEntity<>(this.companyService.findAll(), OK);
    }


    @Operation(summary = "Get all companies with pagination", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/page/{page}")
    @Override
    public ResponseEntity<Page<Company>> findAll(@PathVariable("page") int page) {
        return new ResponseEntity<>(this.companyService.findAll(PageRequest.of(page, PAGE_SIZE)), OK);
    }

    @Operation(summary = "Create a new company", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response201, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = response400, description = badRequest, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Company> save(@Valid @RequestBody Company entity, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.companyService.save(entity, result), CREATED);
    }

    @Operation(summary = "Update a specific company", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = response400, description = badRequest, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize("hasAnyAuthority({'HELPDESK','COORDINATOR','MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Company> update(@Valid @RequestBody Company entity, BindingResult result) throws BadRequestException, NoResultException {
        return new ResponseEntity<>(this.companyService.update(entity, result), OK);
    }

    @Operation(summary = "Get company by ID", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Company> findById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.companyService.findById(id), OK);
    }

    @Operation(summary = "Disable a company by ID", security = {
            @SecurityRequirement(name = bearerAuth)}, responses = {
            @ApiResponse(responseCode = response200, description = successful, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = Occupation.class))),
            @ApiResponse(responseCode = response404, description = notFound, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class))),
            @ApiResponse(responseCode = response401, description = unauthorized, content = @Content(mediaType = applicationJson, schema = @Schema(implementation = CustomHttpResponse.class)))})
    @ResponseStatus(value = OK)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority({'MANAGER','ADMIN'})")
    @Override
    public ResponseEntity<Company> deleteById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.companyService.deleteById(id), OK);
    }


}
