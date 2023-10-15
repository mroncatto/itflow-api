package io.github.mroncatto.itflow.domain.device.service;

import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.device.dto.DeviceCategoryRequestDto;
import io.github.mroncatto.itflow.domain.device.model.IDeviceCategoryService;
import io.github.mroncatto.itflow.domain.device.entity.DeviceCategory;
import io.github.mroncatto.itflow.infrastructure.persistence.IDeviceCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeviceCategoryService extends AbstractService implements IDeviceCategoryService {
    private final MessageService messageService;
    private final IDeviceCategoryRepository repository;

    @Override
    public List<DeviceCategory> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public List<DeviceCategory> findByDeviceIsNotNull() {
        return this.repository.findByDeviceIsNotNull();
    }

    @Override
    public DeviceCategory save(DeviceCategoryRequestDto deviceCategoryRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var deviceCategory = new DeviceCategory();
        BeanUtils.copyProperties(deviceCategoryRequestDto, deviceCategory);
        log.debug(">>>CREATING DEVICE CATEGORY: {}", deviceCategoryRequestDto.toString());
        return this.repository.save(deviceCategory);
    }

    @Override
    public DeviceCategory update(DeviceCategoryRequestDto deviceCategoryRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        DeviceCategory category = this.findById(deviceCategoryRequestDto.getId());
        category.setName(deviceCategoryRequestDto.getName());
        log.debug(">>>UPDATING DEVICE CATEGORY: {}", deviceCategoryRequestDto.toString());
        return this.repository.save(category);
    }

    @Override
    public DeviceCategory findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("device_category")));
    }

    @Override
    public Page<DeviceCategory> findAll(Pageable pageable, String filter) {
        log.debug(">>>FILTERING DEVICE CATEGORY BY: {}", filter);
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public DeviceCategory deleteById(Long id) throws NoResultException {
        DeviceCategory category = this.findById(id);
        category.setActive(false);
        log.debug(">>>DELETING DEVICE CATEGORY BY: {}", id);
        return this.repository.save(category);
    }
}
