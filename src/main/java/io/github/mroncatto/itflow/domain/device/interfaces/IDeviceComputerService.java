package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.device.model.Device;
import io.github.mroncatto.itflow.domain.device.model.DeviceComputer;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;

public interface IDeviceComputerService {
    Device updateComputer(DeviceComputer entity, Long id, BindingResult result) throws BadRequestException;
    Device deleteComputerFromDevice(Long id) throws NoResultException;
}
