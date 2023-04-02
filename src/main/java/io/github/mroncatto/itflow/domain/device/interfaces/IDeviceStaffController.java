package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.device.model.Device;
import io.github.mroncatto.itflow.domain.device.model.DeviceStaff;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.NoResultException;
import javax.validation.Valid;

public interface IDeviceStaffController {
    ResponseEntity<Device> updateStaff(Long id, @Valid @RequestBody DeviceStaff entity, BindingResult result) throws BadRequestException;
    ResponseEntity<Device> deleteStaffFromDevice(Long id) throws NoResultException;
}
