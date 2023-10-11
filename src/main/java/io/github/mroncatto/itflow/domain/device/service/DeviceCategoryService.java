package io.github.mroncatto.itflow.domain.device.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.device.dto.DeviceCategoryDto;
import io.github.mroncatto.itflow.domain.device.model.IDeviceCategoryService;
import io.github.mroncatto.itflow.domain.device.entity.DeviceCategory;
import io.github.mroncatto.itflow.infrastructure.persistence.IDeviceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceCategoryService extends AbstractService implements IDeviceCategoryService {

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
    public DeviceCategory save(DeviceCategoryDto deviceCategoryDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        var deviceCategory = new DeviceCategory();
        BeanUtils.copyProperties(deviceCategoryDto, deviceCategory);
        return this.repository.save(deviceCategory);
    }

    @Override
    public DeviceCategory update(DeviceCategoryDto deviceCategoryDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        DeviceCategory category = this.findById(deviceCategoryDto.getId());
        category.setName(deviceCategoryDto.getName());
        return this.repository.save(category);
    }

    @Override
    public DeviceCategory findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("DEVICE CATEGORY NOT FOUND"));
    }

    @Override
    public Page<DeviceCategory> findAll(Pageable pageable, String filter) {
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public DeviceCategory deleteById(Long id) throws NoResultException {
        DeviceCategory category = this.findById(id);
        category.setActive(false);
        return this.repository.save(category);
    }
}
