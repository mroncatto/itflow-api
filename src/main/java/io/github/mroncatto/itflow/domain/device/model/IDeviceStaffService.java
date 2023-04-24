package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceStaff;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface IDeviceStaffService {
    Device updateStaff(DeviceStaff entity, Long id, BindingResult result) throws BadRequestException;
    Device deleteStaffFromDevice(Long id) throws NoResultException;
}
