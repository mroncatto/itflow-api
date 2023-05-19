package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.NoResultException;
import javax.validation.Valid;

public interface IDeviceComputerController {

    ResponseEntity<Device> updateComputer(Long id, @Valid @RequestBody DeviceComputer entity, BindingResult result) throws BadRequestException;
    ResponseEntity<Device> deleteComputerFromDevice(Long id) throws NoResultException;
}
