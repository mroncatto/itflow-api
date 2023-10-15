package io.github.mroncatto.itflow.infrastructure.web.controller.device;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.application.config.constant.EndpointUrlConstant;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.DeviceCategoryRequestDto;
import io.github.mroncatto.itflow.domain.device.entity.DeviceCategory;
import io.github.mroncatto.itflow.domain.device.model.IDeviceCategoryService;
import io.github.mroncatto.itflow.domain.user.helper.RolesHelper;
import io.github.mroncatto.itflow.infrastructure.annotation.CustomSwaggerList;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.github.mroncatto.itflow.application.config.constant.ControllerConstant.PAGE_SIZE;
import static io.github.mroncatto.itflow.domain.user.helper.RolesHelper.HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = EndpointUrlConstant.deviceCategory)
@Tag(name = "Device", description = "Devices")
@RequiredArgsConstructor
public class DeviceCategoryController {
    private final IDeviceCategoryService service;


    @CustomSwaggerList(summary = "Get all device categories", schema = DeviceCategory.class)
    //FIXME: Nao aplica o schema deixando como object
    @ResponseStatus(value = OK)
    @GetMapping()
    public ResponseEntity<List<DeviceCategory>> findAll() {
        return new ResponseEntity<>(this.service.findAll(), OK);
    }

   // @CustomSwaggerPost(summary = "Create a new device category", schema = DeviceCategory.class)
    @ResponseStatus(value = CREATED)
    @PostMapping()
    @PreAuthorize(RolesHelper.HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<DeviceCategory> save(@RequestBody @Validated(DeviceCategoryRequestDto.DeviceCategoryView.DeviceCategoryPost.class)
                                               @JsonView(DeviceCategoryRequestDto.DeviceCategoryView.DeviceCategoryPost.class) DeviceCategoryRequestDto deviceCategoryRequestDto, BindingResult result) throws BadRequestException {
        return new ResponseEntity<>(this.service.save(deviceCategoryRequestDto, result), CREATED);
    }

   // @CustomSwaggerPut(summary = "Update a specific device category", schema = DeviceCategory.class)
    @ResponseStatus(value = OK)
    @PutMapping()
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<DeviceCategory> update(@RequestBody @Validated(DeviceCategoryRequestDto.DeviceCategoryView.DeviceCategoryPut.class)
                                                 @JsonView(DeviceCategoryRequestDto.DeviceCategoryView.DeviceCategoryPut.class) DeviceCategoryRequestDto deviceCategoryRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        return new ResponseEntity<>(this.service.update(deviceCategoryRequestDto, result), OK);
    }

    //@CustomSwaggerFindOne(summary = "Get device category by ID", schema = DeviceCategory.class)
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.id)
    public ResponseEntity<DeviceCategory> findById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.findById(id), OK);
    }

    //@CustomSwaggerList(summary = "Get all device categories with pagination", schema = Page.class)
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.page)
    public ResponseEntity<Page<DeviceCategory>> findAll(@PathVariable("page") int page, @RequestParam(required = false, name = "filter") String filter) {
        return new ResponseEntity<>(this.service.findAll(PageRequest.of(page, PAGE_SIZE), filter), OK);
    }

   // @CustomSwaggerList(summary = "Get all distinct device categories being used by the device module", schema = Page.class)
    @ResponseStatus(value = OK)
    @GetMapping(EndpointUrlConstant.filterDevice)
    public ResponseEntity<List<DeviceCategory>> findAllUsingByDevice() {
        return new ResponseEntity<>(this.service.findByDeviceIsNotNull(), OK);
    }

   // @CustomSwaggerFindOne(summary = "Disable a device category by ID", schema = DeviceCategory.class)
    @ResponseStatus(value = OK)
    @DeleteMapping(EndpointUrlConstant.id)
    @PreAuthorize(HELPDESK_OR_COORDINATOR_OR_MANAGER_OR_ADMIN)
    public ResponseEntity<DeviceCategory> deleteById(@PathVariable("id") Long id) throws NoResultException {
        return new ResponseEntity<>(this.service.deleteById(id), OK);
    }
}
