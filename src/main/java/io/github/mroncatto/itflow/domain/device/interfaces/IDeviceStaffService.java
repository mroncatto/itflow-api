package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.device.model.Device;
import io.github.mroncatto.itflow.domain.device.model.DeviceStaff;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface IDeviceStaffService {
    Device updateStaff(DeviceStaff entity, Long id, BindingResult result) throws BadRequestException;
    Device deleteStaffFromDevice(Long id) throws NoResultException;
}
