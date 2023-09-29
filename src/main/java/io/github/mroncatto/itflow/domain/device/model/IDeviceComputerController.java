package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerCpu;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;

public interface IDeviceComputerController {

    ResponseEntity<Device> updateComputer(Long id, DeviceComputer entity, BindingResult result) throws BadRequestException;
    ResponseEntity<Device> deleteComputerFromDevice(Long id) throws NoResultException;
    ResponseEntity<Device> addDeviceComputerCpu(Long id, DeviceComputerCpu entity, BindingResult result) throws NoResultException, BadRequestException;
}
