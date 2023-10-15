package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.device.dto.DeviceCategoryRequestDto;
import io.github.mroncatto.itflow.domain.device.entity.DeviceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

import java.util.List;

public interface IDeviceCategoryService extends IAbstractService<DeviceCategory, DeviceCategoryRequestDto> {
    List<DeviceCategory> findByDeviceIsNotNull();
    DeviceCategory findById(Long id) throws NoResultException;
    Page<DeviceCategory> findAll(Pageable pageable, String filter);
    DeviceCategory deleteById(Long id) throws NoResultException;
}
