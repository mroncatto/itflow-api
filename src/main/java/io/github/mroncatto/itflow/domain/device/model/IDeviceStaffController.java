package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceStaff;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

public interface IDeviceStaffController {
    ResponseEntity<Device> updateStaff(Long id, @Valid @RequestBody DeviceStaff entity, BindingResult result) throws BadRequestException;
    ResponseEntity<Device> deleteStaffFromDevice(Long id) throws NoResultException;
}
