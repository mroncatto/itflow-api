package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.DeviceStaffDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.persistence.NoResultException;
import org.springframework.validation.BindingResult;

public interface IDeviceStaffService {
    Device updateStaff(DeviceStaffDto deviceStaffDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteStaffFromDevice(Long id) throws NoResultException;
}
