package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerCpuDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.persistence.NoResultException;
import org.springframework.validation.BindingResult;

public interface IDeviceComputerService {
    Device updateComputer(DeviceComputerDto deviceComputerDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteComputerFromDevice(Long id) throws NoResultException;
    Device addDeviceComputerCpu(DeviceComputerCpuDto deviceComputerCpuDto, Long id, BindingResult result) throws BadRequestException;
}
