package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerCpu;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface IDeviceComputerService {
    Device updateComputer(DeviceComputer entity, Long id, BindingResult result) throws BadRequestException;
    Device deleteComputerFromDevice(Long id) throws NoResultException;
    Device addDeviceComputerCpu(DeviceComputerCpu entity, Long id, BindingResult result) throws BadRequestException;
}
