package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerCpuRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerRequestDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.persistence.NoResultException;
import org.springframework.validation.BindingResult;

public interface IDeviceComputerService {
    Device updateComputer(DeviceComputerRequestDto deviceComputerRequestDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteComputerFromDevice(Long id) throws NoResultException;
    Device addDeviceComputerCpu(DeviceComputerCpuRequestDto deviceComputerCpuRequestDto, Long id, BindingResult result) throws BadRequestException;
}
